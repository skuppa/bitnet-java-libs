/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.payment.order.Order;
import io.bitnet.model.payment.order.OrderCreate;
import io.bitnet.model.payment.order.OrderUpdate;
import io.bitnet.model.payment.order.Orders;

import java.util.List;

/**
 * The order is the core entity of the payment process and contains details regarding the product or service that is offered by a merchant.
 * <p>
 * For example, pricing currency, payer details, shipping details etc.
 * <p>
 * The price and pricing currency specified in the order by the merchant is the same as that in the merchant's shopping cart, for example $142.98 USD.
 * <p>
 * Digital currency is the responsibility of the invoice and not of the order.
 */
public interface OrderService {

    /**
     * Get the list of orders that belong to an particular account.
     *
     * @param accountId Account that Orders belong to e.g. 821baab2-90e1-a32d-2094-b31da1db6a42.
     * @param state     One or more states to filter Orders by.
     * @param offset    Start index position for list of entities returned e.g. 5.
     *                  Range 0 to 999999999.
     * @param limit     Max number of orders per page e.g. 10.
     *                  Range 1 to 999999999
     * @return The orders.
     */
    Orders getOrders(String accountId, List<Order.State> state, int offset, int limit);

    /**
     * Create a new Order.
     * <p>
     * An order can only be linked to one invoice.
     * <p>
     * A previously created *payerId* , along with your Bitnet *accountId*. must be supplied when creating an order.
     * <p>
     * A payer may be linked to multiple orders.
     *
     * @param entity e.g. order/order-create.json.
     * @return The created order.
     */
    Order createOrder(OrderCreate entity);

    /**
     * Get order.
     *
     * @param orderId The id of the order e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The order.
     */
    Order getOrder(String orderId);

    /**
     * Update the state of an existing order.
     * <p>
     * The state of an order can be updated to **CANCELED** if the order has not yet been invoiced.
     *
     * @param entity  e.g. order/order-update.json.
     * @param orderId The id of the order e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The updated order.
     */
    Order updateOrder(OrderUpdate entity, String orderId);
}
