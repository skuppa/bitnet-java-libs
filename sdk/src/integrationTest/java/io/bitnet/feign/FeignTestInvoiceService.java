/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.model.payment.invoice.Invoice;

/**
 * This differs to the Invoice Service offered to customers as it uses an invoice which does not rely on
 * the external enum to set values.
 */
@Headers({"Content-Type: application/json"})
public interface FeignTestInvoiceService {

    @RequestLine("PUT v1/invoices/{invoiceId}")
    Invoice updateInvoice(@Param("invoiceId") String invoiceId, TestInvoiceUpdate entity);
}
