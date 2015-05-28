/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.payment.*;

import java.util.List;

/**
* The order is the core entity of the payment process and contains details regarding the product or service that is offered by a merchant. 
* <p>
* For example, pricing currency, shipping details etc.
* <p>
* The price and pricing currency specified in the order by the merchant is the same as that in the merchant's shopping cart, for example $142.98 USD. 
* <p>
* Digital currency is the responsibility of the invoice and not of the order.
*/
public interface OrderService {

        /**
        * Get the list of orders that belong to an particular account.
        *
        * @param accountId Account that Orders belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param reference Reference for a specific order eg. Customer-Order-0000-1234-5678
        * @param state Filter orders by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. CANCELED
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Orders
        */
        Orders getOrders(String accountId, String reference, List<Order.State> state, int offset, int limit);

        /**
        * Get the list of orders that belong to an particular account.
        *
        * @param accountId Account that Orders belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param state Filter orders by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. CANCELED
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Orders
        */
        Orders getOrders(String accountId, List<Order.State> state, int offset, int limit);

        /**
        * Get the list of orders that belong to an particular account.
        *
        * @param reference Reference for a specific order eg. Customer-Order-0000-1234-5678
        *
        * @return The Orders
        */
        Orders getOrders(String reference);

        /**
        * Create a new Order.
        * <p>.
        * An order can only be linked to one invoice.  .
        * <p>.
        * A previously created *payerId* , along with your Bitnet *accountId*. must be supplied when creating an order.  .
        * <p>.
        * A payer may be linked to multiple orders.     .
        *
        * @param entity  eg. OrderCreate
        *
        * @return The Order
        */
        Order createOrder(OrderCreate entity);

        /**
        * Get order.
        *
        * @param orderId The id of the order eg. f47ac10b-58cc-4372-a567-5a02b2c3e479
        *
        * @return The Order
        */
        Order getOrder(String orderId);

        /**
        * Update the state of an existing order..
        * <p>.
        * The state of an order can be updated to **CANCELED** if the order has not yet been invoiced..
        * <p>.
        *
        * @param entity  eg. OrderUpdate
        * @param orderId The id of the order eg. f47ac10b-58cc-4372-a567-5a02b2c3e479
        *
        * @return The Order
        */
        Order updateOrder(OrderUpdate entity, String orderId);

}
