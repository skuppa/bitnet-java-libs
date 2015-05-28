/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.api.InvoiceService;
import io.bitnet.model.payment.Invoice;
import io.bitnet.model.payment.InvoiceCreate;
import io.bitnet.model.payment.InvoiceUpdate;
import io.bitnet.model.payment.Invoices;

import java.util.List;

/**
 * Feign representation of InvoiceService.
 */
@Headers({"Content-Type: application/json"})
interface FeignInvoiceService extends InvoiceService {



    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&reference={reference}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("accountId") String accountId, @Param("reference") String reference, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&orderId={orderId}&paymentAddress={paymentAddress}&reference={reference}&state={state}" +
            "&transitionedTo={transitionedTo}&transitionedOn={transitionedOn}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("accountId") String accountId, @Param("orderId") String orderId, @Param("paymentAddress") String paymentAddress, @Param("reference")
    String reference, @Param("state") List<Invoice.State> state, @Param("transitionedTo") Invoice.State transitionedTo, @Param("transitionedOn") String transitionedOn,
                         @Param("offset") int offset, @Param("limit") int limit);


    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&state={state}&orderId={orderId}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("accountId") String accountId,
                         @Param("state") List<Invoice.State> state, @Param("orderId") String orderId, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&paymentAddress={paymentAddress}&state={state}&orderId={orderId}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("accountId") String accountId, @Param("paymentAddress") String paymentAddress,
                         @Param("state") List<Invoice.State> state, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&transitionedTo={transitionedTo}&transitionedOn={transitionedOn}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("transitionedTo") Invoice.State transitionedTo,
                         @Param("transitionedOn") String transitionedOn, @Param("accountId") String accountId, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/invoices?accountId={accountId}&state={state}&offset={offset}&limit={limit}")
    Invoices getInvoices(@Param("accountId") String accountId, @Param("state") List<Invoice.State> state, @Param("offset") int offset, @Param("limit") int limit);


    @Override
    @RequestLine("POST v1/invoices")
    Invoice createInvoice(InvoiceCreate entity);


    @Override
    @RequestLine("GET v1/invoices/{invoiceId}")
    Invoice getInvoice(@Param("invoiceId") String invoiceId);


    @Override
    @RequestLine("PUT v1/invoices/{invoiceId}")
    Invoice updateInvoice(InvoiceUpdate entity, @Param("invoiceId") String invoiceId);
}
