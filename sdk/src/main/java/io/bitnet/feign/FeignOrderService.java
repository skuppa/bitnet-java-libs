/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.api.OrderService;
import io.bitnet.model.payment.Order;
import io.bitnet.model.payment.OrderCreate;
import io.bitnet.model.payment.OrderUpdate;
import io.bitnet.model.payment.Orders;

import java.util.List;

/**
 * Feign representation of OrderService.
 */
@Headers({"Content-Type: application/json"})
interface FeignOrderService extends OrderService {

    @Override
    @RequestLine("GET v1/orders?accountId={accountId}&reference={reference}&state={state}&offset={offset}&limit={limit}")
    Orders getOrders(@Param("accountId") String accountId, @Param("reference") String reference, @Param("state") List<Order.State> state, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/orders?accountId={accountId}&state={state}&offset={offset}&limit={limit}")
    Orders getOrders(@Param("accountId") String accountId, @Param("state") List<Order.State> state, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("POST v1/orders")
    Order createOrder(OrderCreate entity);


    @Override
    @RequestLine("GET v1/orders/{orderId}")
    Order getOrder(@Param("orderId") String orderId);


    @Override
    @RequestLine("PUT v1/orders/{orderId}")
    Order updateOrder(OrderUpdate entity, @Param("orderId") String orderId);
}
