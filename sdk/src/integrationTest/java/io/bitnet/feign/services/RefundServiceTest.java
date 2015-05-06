/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.services;

import io.bitnet.Bitnet;
import io.bitnet.api.RefundService;
import io.bitnet.core.exceptions.BitnetRequestCouldNotBeProcessedException;
import io.bitnet.feign.FeignTestServiceProvider;
import io.bitnet.feign.TestInvoiceUpdate;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payment.invoice.Invoice;
import io.bitnet.model.payment.invoice.InvoiceCreate;
import io.bitnet.model.payment.order.Order;
import io.bitnet.model.payment.order.OrderCreate;
import io.bitnet.model.refund.refund.Refund;
import io.bitnet.model.refund.refund.RefundCreate;
import io.bitnet.model.refund.refund.Refunds;
import io.bitnet.model.refund.refund.Requested;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.bitnet.AssertionHelper.assertIsUUID;
import static io.bitnet.TestCredentials.*;
import static io.bitnet.TestFactory.getPayerCreateWithAllInformation;
import static io.bitnet.TestFactory.getRandomId;
import static io.bitnet.TestUtilities.newBitnetService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

/**
 * The integration tests for {@link io.bitnet.api.RefundService}.
 */
public class RefundServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RefundServiceTest.class);
    private static final String EMAIL = "payer.1@bitnet.io";


    private RefundService target;
    private Bitnet bitnet;
    private Invoice paidInvoice;

    @Before
    public void setUp() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        bitnet = newBitnetService();
        target = bitnet.refundService();
        paidInvoice = getPaidInvoice();
    }


    /**
     * Check if internal client id is provided otherwise ignore tests.
     *
     * @return if internal client id is configured.
     */
    private boolean isInternalClientNotAvailable() {
        try {
            return StringUtils.isBlank(internalClientId());
        } catch (Exception e) {
            return true;
        }
    }

    @Test
    public void shouldCreatePartialRefund() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withAmount("10.00")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(paidInvoice.getId());

        Refund refund = target.createRefund(newRefund);

        assertThat(refund.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(refund.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(refund.getState(), is(equalTo(Refund.State.OPEN)));
        assertThat(refund.getPaymentAddress(), is(equalTo("mm4FdvNDm173WiQosCvhqpQdThsE7vvSCp")));
        assertThat(refund.getInstruction(), is(equalTo(Refund.Instruction.PARTIAL)));
    }

    @Test
    public void shouldCreateFullRefund() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withInstruction(Refund.Instruction.FULL)
                .withInvoiceId(paidInvoice.getId());

        Refund refund = target.createRefund(newRefund);

        assertThat(refund.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(refund.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(refund.getState(), is(equalTo(Refund.State.OPEN)));
        assertThat(refund.getPaymentAddress(), is(equalTo("mm4FdvNDm173WiQosCvhqpQdThsE7vvSCp")));
        assertThat(refund.getInstruction(), is(equalTo(Refund.Instruction.FULL)));
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenIncludingAmountForAFullRefund() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withAmount("55.12")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.FULL)
                .withInvoiceId(paidInvoice.getId());

        try {
            target.createRefund(newRefund);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("additional properties are not allowed"));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenInitiatingARefundOnAnOpenInvoice() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        Invoice openInvoice = getOpenInvoice();
        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withAmount("10.12")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(openInvoice.getId());

        try {
            target.createRefund(newRefund);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Cannot initiate a PARTIAL refund on invoice"));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenNoMatchingInvoice() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withAmount("10.12")
                .withCurrency(Requested.Currency.BBD)
                .withInstruction(Refund.Instruction.PARTIAL)
                .withInvoiceId(getRandomId());

        try {
            target.createRefund(newRefund);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Unable to find invoice"));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenCreatingADuplicateRefund() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withInstruction(Refund.Instruction.FULL)
                .withInvoiceId(paidInvoice.getId());

        try {
            target.createRefund(newRefund);
            target.createRefund(newRefund);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("A full refund has already been initiated on invoice"));
        }
    }

    @Test
    public void shouldGetOpenRefundsForInvoice() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        createFullRefund();

        Refunds refunds = target.getRefunds(testAccountId(), paidInvoice.getId(), Refund.State.OPEN, 0, 10);

        assertThat(refunds.getSize(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldGetOpenRefundsForAccount() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        createFullRefund();

        Refunds refunds = target.getRefunds(testAccountId(), Refund.State.OPEN, 0, 10);

        assertThat(refunds.getSize(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldRefundsTransitionedToOpen() {
        if (isInternalClientNotAvailable()) {
            return;
        }

        createFullRefund();
        String transitionedDate = String.format("%s..%s", new DateTime(DateTimeZone.UTC).minusMinutes(10).toString(), new DateTime(DateTimeZone.UTC).toString());

        Refunds refunds = target.getRefunds(Refund.State.OPEN, transitionedDate, testAccountId(), 0, 10);

        assertThat(refunds.getSize(), is(greaterThanOrEqualTo(1)));
    }

    private Refund createFullRefund() {
        RefundCreate newRefund = new RefundCreate()
                .withAccountId(testAccountId())
                .withInstruction(Refund.Instruction.FULL)
                .withInvoiceId(paidInvoice.getId());

        return target.createRefund(newRefund);
    }

    private Invoice getPaidInvoice() {
        Invoice openInvoice = getOpenInvoice();

        FeignTestServiceProvider sp = new FeignTestServiceProvider(internalClientId(), internalSecret());

        TestInvoiceUpdate invoiceToUpdate = new TestInvoiceUpdate().withState("PENDING");
        sp.invoiceService().updateInvoice(openInvoice.getId(), invoiceToUpdate);
        invoiceToUpdate = new TestInvoiceUpdate().withState("PAID");

        return sp.invoiceService().updateInvoice(openInvoice.getId(), invoiceToUpdate);
    }

    private Invoice getOpenInvoice() {
        PayerCreate newPayer = getPayerCreateWithAllInformation(EMAIL);
        Payer createdPayer = bitnet.payerService().createPayer(newPayer);

        OrderCreate newOrder = new OrderCreate()
                .withAccountId(testAccountId())
                .withPayerId(createdPayer.getId())
                .withCurrency(Order.Currency.BBD)
                .withTotalAmount("55.12");
        Order order = bitnet.orderService().createOrder(newOrder);

        InvoiceCreate newInvoice = new InvoiceCreate().withOrderId(order.getId()).withAccountId(testAccountId());

        return bitnet.invoiceService().createInvoice(newInvoice);
    }
}
