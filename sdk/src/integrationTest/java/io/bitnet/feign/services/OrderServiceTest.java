/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.services;

import io.bitnet.Bitnet;
import io.bitnet.api.OrderService;
import io.bitnet.core.exceptions.BitnetAccessDeniedException;
import io.bitnet.core.exceptions.BitnetRequestCouldNotBeProcessedException;
import io.bitnet.core.exceptions.BitnetRequestForbiddenException;
import io.bitnet.core.exceptions.BitnetResourceNotFoundException;
import io.bitnet.feign.TestRequestCounterInterceptor;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payment.order.Order;
import io.bitnet.model.payment.order.OrderCreate;
import io.bitnet.model.payment.order.OrderUpdate;
import io.bitnet.model.payment.order.Orders;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.bitnet.AssertionHelper.*;
import static io.bitnet.TestCredentials.testAccountId;
import static io.bitnet.TestFactory.*;
import static io.bitnet.TestUtilities.newBitnetService;
import static io.bitnet.TestUtilities.newBitnetServiceWithInvalidClientCredentials;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

/**
 * The integration tests for {@link io.bitnet.feign.FeignOrderService}.
 */
public class OrderServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);
    private static final String EMAIL = "payer.1@bitnet.io";
    private OrderService target;
    private Bitnet bitnet;
    private Payer newPayer;

    @Before
    public void setUp() {
        bitnet = newBitnetService();
        target = bitnet.orderService();
        newPayer = getNewPayer();
    }

    @Test
    public void shouldCreateOrderOnlyWithRequiredInfo() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();

        Order createdOrder = target.createOrder(newOrder);

        logger.info("Created Order: {}", createdOrder);

        assertThat(createdOrder.getTotalAmount(), is(equalTo("55.12")));
        assertThat(createdOrder.getState(), is(equalTo(Order.State.OPEN)));
        assertThat(createdOrder.getCurrency(), is(equalTo(Order.Currency.BBD)));
        assertIsUUID(createdOrder.getId());
        assertThat(createdOrder.getCreatedAt(), is(not(emptyOrNullString())));
    }

    @Test
    public void shouldCreateOrderWithShippingDetails() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();
        newOrder.setShippingAmount("1.99");
        newOrder.setShipping(getShipping());

        Order createdOrder = target.createOrder(newOrder);

        logger.info("Created Order: {}", createdOrder);

        assertThat(createdOrder.getShippingAmount(), is(equalTo("1.99")));
        assertThat(createdOrder.getShipping().getFirstName(), is(equalTo(newOrder.getShipping().getFirstName())));
        assertThat(createdOrder.getShipping().getLastName(), is(equalTo(newOrder.getShipping().getLastName())));
        assertThat(createdOrder.getShipping().getMethod(), is(equalTo(newOrder.getShipping().getMethod())));
        assertThat(createdOrder.getShipping().getAddress(), is(equalTo(newOrder.getShipping().getAddress())));
        assertThatShippingContactNumbersMatch(newOrder.getShipping().getContactNumbers(), createdOrder.getShipping().getContactNumbers());
    }

    @Test
    public void shouldCreateOrderWithItems() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();
        newOrder.setItems(getOrderItems());

        Order createdOrder = target.createOrder(newOrder);

        logger.info("Created Order: {}", createdOrder);

        assertThatOrderItemsMatch(newOrder.getItems(), createdOrder.getItems());
    }

    @Test
    public void shouldCreateOrderWithDescriptiveInfo() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();
        newOrder.setDesc("Order of ducks");
        newOrder.setReference(newPayer.getReference());
        newOrder.setChannelIdentifier(Order.ChannelIdentifier.RETAIL);
        newOrder.setIntegrationId(getRandomId());
        newOrder.setTaxAmount("99.00");
        newOrder.setAmount("112.00");

        Order createdOrder = target.createOrder(newOrder);

        logger.info("Created Order: {}", createdOrder);

        assertThat(createdOrder.getDesc(), is(equalTo(newOrder.getDesc())));
        assertThat(createdOrder.getReference(), is(equalTo(newOrder.getReference())));
        assertThat(createdOrder.getChannelIdentifier(), is(equalTo(newOrder.getChannelIdentifier())));
        assertThat(createdOrder.getIntegrationId(), is(equalTo(newOrder.getIntegrationId())));
        assertThat(createdOrder.getTaxAmount(), is(equalTo(newOrder.getTaxAmount())));
        assertThat(createdOrder.getAmount(), is(equalTo(newOrder.getAmount())));
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenAccountDetailsInvalid() {
        OrderCreate newOrder = new OrderCreate()
                .withAccountId(getRandomId())
                .withPayerId(newPayer.getId())
                .withCurrency(Order.Currency.BBD)
                .withTotalAmount("55.12");

        try {
            target.createOrder(newOrder);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldRetryAuthenticationRequestWhenBitnetAccessDeniedErrorEncountered() {
        TestRequestCounterInterceptor requestCounterInterceptor = new TestRequestCounterInterceptor();
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();

        try {
            newBitnetServiceWithInvalidClientCredentials(requestCounterInterceptor).orderService().createOrder(newOrder);
            fail();
        } catch (BitnetAccessDeniedException exception) {
            logger.info("Bitnet Access Denied Exception: {}", exception);
            // Note: This is six times as the call to auth is made twice for both of the calls to order service.
            assertThat(requestCounterInterceptor.numberOfRequests, is(equalTo(6)));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenCreatingOrderForPayerWhichDoesNotExist() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();
        newOrder.setPayerId(getRandomId());

        try {
            target.createOrder(newOrder);
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request Could Not Be Processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldBeAbleToGetOpenOrders() {
        List<Order.State> openOrders = new ArrayList<Order.State>();
        openOrders.add(Order.State.OPEN);

        Orders orders = target.getOrders(testAccountId(), openOrders, 0, 10);


        assertThat(orders.getSize(), is(greaterThanOrEqualTo(10)));
    }

    @Test
    public void shouldGetEmptyListWhenGettingOrdersForInvalidAccountId() {
        List<Order.State> openOrders = new ArrayList<Order.State>();
        openOrders.add(Order.State.OPEN);
        assertThat(target.getOrders(getRandomId(), openOrders, 0, 10).getSize(), is(0));
        assertThat(target.getOrders(getRandomId(), openOrders, 0, 10).getOrders(), is(empty()));

    }

    @Test
    public void shouldCancelAnOrder() {
        Order createdOrder = createOrderWithOnlyRequiredInfo();

        OrderUpdate orderToCancel = new OrderUpdate().withState(Order.State.CANCELED);
        Order cancelledOrder = target.updateOrder(orderToCancel, createdOrder.getId());

        assertThat(cancelledOrder.getState(), is(equalTo(Order.State.CANCELED)));
    }

    @Test
    public void shouldGetBitnetResourceNotFoundExceptionWhenTryingToCancelAnOrderWhichDoesNotExist() {
        OrderUpdate orderToCancel = new OrderUpdate().withState(Order.State.CANCELED);

        try {
            target.updateOrder(orderToCancel, getRandomId());
            fail();
        } catch (BitnetResourceNotFoundException exception) {
            logger.info("Bitnet Resource Not Found Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldGetABitnetRequestForbiddenExceptionWhenChangingAnOrderToInvoiced() {
        Order createdOrder = createOrderWithOnlyRequiredInfo();
        OrderUpdate orderToCancel = new OrderUpdate().withState(Order.State.INVOICED);

        try {
            target.updateOrder(orderToCancel, createdOrder.getId());
            fail();
        } catch (BitnetRequestForbiddenException exception) {
            logger.info("Bitnet Request Forbidden Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    @Test
    public void shouldGetOrder() {
        Order createdOrder = createOrderWithOnlyRequiredInfo();

        Order fetchedOrder = target.getOrder(createdOrder.getId());

        assertThat(fetchedOrder.getTotalAmount(), is(equalTo(createdOrder.getTotalAmount())));
    }

    @Test
    public void shouldGetBitnetResourceNotFoundExceptionWhenGettingOrderWhichDoesNotExist() {
        try {
            target.getOrder(getRandomId());
            fail();
        } catch (BitnetResourceNotFoundException exception) {
            logger.info("Bitnet Resource Not Found Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    private Order createOrderWithOnlyRequiredInfo() {
        OrderCreate newOrder = getOrderToCreateWithOnlyRequiredInfo();
        return target.createOrder(newOrder);
    }

    private Payer getNewPayer() {
        PayerCreate payerToCreate = getPayerCreateWithAllInformation(EMAIL);
        return bitnet.payerService().createPayer(payerToCreate);
    }

    private OrderCreate getOrderToCreateWithOnlyRequiredInfo() {
        return new OrderCreate()
                .withAccountId(testAccountId())
                .withPayerId(newPayer.getId())
                .withCurrency(Order.Currency.BBD)
                .withTotalAmount("55.12");
    }
}
