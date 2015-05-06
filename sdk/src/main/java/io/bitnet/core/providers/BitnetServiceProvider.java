/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.core.providers;

import io.bitnet.api.*;

/**
 * Interface describing service provider for bitnet api's.
 */
public interface BitnetServiceProvider {
    /**
     * Get invoice service.
     *
     * @return invoice service
     */
    InvoiceService invoiceService();

    /**
     * Get order service.
     *
     * @return order service
     */
    OrderService orderService();

    /**
     * Get payer service.
     *
     * @return payer service
     */
    PayerService payerService();

    /**
     * Get refund service.
     *
     * @return refund service
     */
    RefundService refundService();
}
