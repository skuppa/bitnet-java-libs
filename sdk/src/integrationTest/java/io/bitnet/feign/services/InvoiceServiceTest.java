/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.services;

import io.bitnet.Bitnet;
import io.bitnet.api.InvoiceService;
import io.bitnet.core.exceptions.BitnetConflictException;
import io.bitnet.core.exceptions.BitnetRequestCouldNotBeProcessedException;
import io.bitnet.core.exceptions.BitnetResourceNotFoundException;
import io.bitnet.model.payer.Payer;
import io.bitnet.model.payer.PayerCreate;
import io.bitnet.model.payment.Invoice;
import io.bitnet.model.payment.InvoiceCreate;
import io.bitnet.model.payment.InvoiceUpdate;
import io.bitnet.model.payment.Invoices;
import io.bitnet.model.payment.Order;
import io.bitnet.model.payment.OrderCreate;
import io.bitnet.model.payment.OrderUpdate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.bitnet.AssertionHelper.assertIsUUID;
import static io.bitnet.TestCredentials.testAccountId;
import static io.bitnet.TestFactory.getPayerCreateWithAllInformation;
import static io.bitnet.TestFactory.getRandomId;
import static io.bitnet.TestUtilities.newBitnetService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

/**
 * The integration tests for {@link io.bitnet.api.InvoiceService}.
 */
public class InvoiceServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PayerServiceTest.class);
    private static final String EMAIL = "payer.1@bitnet.io";
    private InvoiceService target;
    private Bitnet bitnet;
    private Order openOrder;

    @Before
    public void setUp() {
        bitnet = newBitnetService();
        target = bitnet.invoiceService();
        openOrder = getOpenOrder();
    }

    @Test
    public void shouldCreateInvoice() {
        InvoiceCreate newInvoice = getInvoiceForOpenOrder();

        Invoice createdInvoice = target.createInvoice(newInvoice);

        assertIsUUID(createdInvoice.getQuote().getQuoteId());
        assertThat(createdInvoice.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(createdInvoice.getExpiresAt(), is(not(emptyOrNullString())));
        assertThat(createdInvoice.getPaymentAddress(), is(not(emptyOrNullString())));
        assertThat(createdInvoice.getPaymentUri(), is(not(emptyOrNullString())));
        assertThat(createdInvoice.getState(), is(equalTo(Invoice.State.OPEN)));
        assertThat(createdInvoice.getAmountReceived(), is(equalTo("0.00000000")));
    }

    @Test
    public void shouldGetBitnetConflictExceptionWhenCreatingDuplicateInvoice() {
        InvoiceCreate newInvoice = getInvoiceForOpenOrder();

        target.createInvoice(newInvoice);
        try {
            target.createInvoice(newInvoice);
            fail();
        } catch (BitnetConflictException exception) {
            logger.info("Bitnet Conflict Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Invoice can only be created on an OPEN order "));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenCreatingInvoiceForInvalidOrder() {
        InvoiceCreate bogusOrderInvoice = new InvoiceCreate().withAccountId(testAccountId()).withOrderId(getRandomId());

        try {
            target.createInvoice(bogusOrderInvoice);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Unable to find Order"));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenCreatingInvoiceForInvalidAccount() {
        InvoiceCreate bogusAccountInvoice = new InvoiceCreate().withAccountId(getRandomId()).withOrderId(openOrder.getId());

        try {
            target.createInvoice(bogusAccountInvoice);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request could not be processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldGetBitnetConflictExceptionWhenCreatingInvoiceForCancelledOrder() {
        OrderUpdate orderToBeCancelled = new OrderUpdate().withState(Order.State.CANCELED);
        Order cancelledOrder = bitnet.orderService().updateOrder(orderToBeCancelled, openOrder.getId());

        InvoiceCreate cancelledOrderInvoice = new InvoiceCreate().withAccountId(testAccountId()).withOrderId(cancelledOrder.getId());

        try {
            target.createInvoice(cancelledOrderInvoice);
            fail();
        } catch (BitnetConflictException exception) {
            logger.info("Bitnet Request Conflict Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Invoice can only be created on an OPEN order"));
        }
    }

    @Test
    public void shouldUpdateInvoice() {
        Invoice createdInvoice = createInvoiceForOpenOrder();
        InvoiceUpdate invoiceToUpdate = new InvoiceUpdate().withState(Invoice.State.CANCELED);

        Invoice updatedInvoice = target.updateInvoice(invoiceToUpdate, createdInvoice.getId());

        assertThat(updatedInvoice.getState(), is(equalTo(Invoice.State.CANCELED)));
    }

    @Test
    public void shouldGetBitnetConflictExceptionWhenUpdatingInvoiceStateToExistingState() {
        Invoice createdInvoice = createInvoiceForOpenOrder();
        InvoiceUpdate invoiceToUpdate = new InvoiceUpdate().withState(Invoice.State.CANCELED);

        target.updateInvoice(invoiceToUpdate, createdInvoice.getId());
        try {
            target.updateInvoice(invoiceToUpdate, createdInvoice.getId());
        } catch (BitnetConflictException exception) {
            logger.info("Bitnet Conflict Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Unable to update Invoice"));
        }
    }

    @Test
    public void shouldGetBitnetResourceNotFoundExceptionWhenUpdatingInvoiceWhichDoesNotExist() {
        InvoiceUpdate invoiceToUpdate = new InvoiceUpdate().withState(Invoice.State.CANCELED);

        try {
            target.updateInvoice(invoiceToUpdate, getRandomId());
        } catch (BitnetResourceNotFoundException exception) {
            logger.info("Bitnet Resource Not Found Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Unable to find Invoice"));
        }
    }

    @Test
    public void shouldGetInvoice() {
        Invoice createdInvoice = createInvoiceForOpenOrder();

        Invoice fetchedInvoice = target.getInvoice(createdInvoice.getId());

        assertThat(fetchedInvoice.getCreatedAt(), is(equalTo(createdInvoice.getCreatedAt())));
    }

    @Test
    public void shouldGetBitnetResourceNotFoundExceptionWhenRequestedInvoiceDoesNotExist() {
        try {
            target.getInvoice(getRandomId());
        } catch (BitnetResourceNotFoundException exception) {
            logger.info("Bitnet Resource Not Found Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldGetOpenInvoices() {
        createInvoiceForOpenOrder();
        List<Invoice.State> states = getStates(Invoice.State.OPEN);

        Invoices invoices = target.getInvoices(testAccountId(), states, 0, 10);

        assertThat(invoices.getSize(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldGetOpenInvoicesForPaymentAddress() {
        Invoice invoice = createInvoiceForOpenOrder();
        List<Invoice.State> states = getStates(Invoice.State.OPEN);

        Invoices invoices = target.getInvoices(testAccountId(), invoice.getPaymentAddress(), states, 0, 10);

        assertThat(invoices.getSize(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldGetOpenInvoicesForOrder() {
        createInvoiceForOpenOrder();
        List<Invoice.State> states = getStates(Invoice.State.OPEN);

        Invoices invoices = target.getInvoices(testAccountId(), states, openOrder.getId(), 0, 10);

        assertThat(invoices.getSize(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldGetNoPendingInvoicesForOpenOrder() {
        createInvoiceForOpenOrder();
        List<Invoice.State> states = getStates(Invoice.State.PENDING);

        Invoices invoices = target.getInvoices(testAccountId(), states, openOrder.getId(), 0, 10);

        assertThat(invoices.getSize(), is(greaterThanOrEqualTo(0)));
    }

    @Test
    public void shouldGetInvoicesWhichHaveTransitionedToOpen() {
        createInvoiceForOpenOrder();
        Invoice.State transitionTo = Invoice.State.OPEN;
        String transitionedDate = String.format("%s..%s", new DateTime(DateTimeZone.UTC).minusMinutes(10).toString(), new DateTime(DateTimeZone.UTC).toString());

        Invoices invoices = target.getInvoices(transitionTo, transitionedDate, testAccountId(), 0, 10);

        assertThat(invoices.getSize(), is(greaterThanOrEqualTo(0)));
    }

    private List<Invoice.State> getStates(Invoice.State state) {
        List<Invoice.State> states = new ArrayList<>();
        states.add(state);
        return states;
    }

    private Invoice createInvoiceForOpenOrder() {
        InvoiceCreate newInvoice = getInvoiceForOpenOrder();
        return target.createInvoice(newInvoice);
    }

    private InvoiceCreate getInvoiceForOpenOrder() {
        return new InvoiceCreate()
                .withAccountId(testAccountId())
                .withOrderId(openOrder.getId());
    }

    private Order getOpenOrder() {
        PayerCreate newPayer = getPayerCreateWithAllInformation(EMAIL);
        Payer createdPayer = bitnet.payerService().createPayer(newPayer);

        OrderCreate newOrder = new OrderCreate()
                .withAccountId(testAccountId())
                .withPayerId(createdPayer.getId())
                .withCurrency(Order.Currency.BBD)
                .withTotalAmount("55.12");
        return bitnet.orderService().createOrder(newOrder);
    }
}
