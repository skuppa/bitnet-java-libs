/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.payment.invoice.Invoice;
import io.bitnet.model.payment.invoice.InvoiceCreate;
import io.bitnet.model.payment.invoice.InvoiceUpdate;
import io.bitnet.model.payment.invoice.Invoices;

import java.util.List;

/**
 * An invoice is the instruction for the payment which is generated for an order.
 * <p>
 * An invoice is required to process a payment in digital currency.
 */
public interface InvoiceService {

    /**
     * Get all invoices, filtered.
     *
     * @param accountId      Account that invoices belong to e.g. 821baab2-90e1-a32d-2094-b31da1db6a42.
     * @param state          One or more states to filter Invoices by.
     * @param orderId        Order that invoices belong to e.g. 1d001ab9-772f-3942-213d-93144fd851da
     * @param offset         Start index position for list of entities returned e.g. 5.
     *                       Range 0 to 999999999
     * @param limit          Max number of invoices per page e.g. 10.
     *                       Range 1 to 999999999.
     * @return The invoices.
     */
    Invoices getInvoices(String accountId, List<Invoice.State> state, String orderId, int offset, int limit);

    /**
     * Get all invoices, filtered.
     *
     * @param accountId      Account that invoices belong to e.g. 821baab2-90e1-a32d-2094-b31da1db6a42.
     * @param paymentAddress Payment address for a specific invoice e.g. mvzssahncsZBXmHWSYk7HdAbVwQR8KRVQk.
     * @param state          One or more states to filter Invoices by.
     * @param offset         Start index position for list of entities returned e.g. 5.
     *                       Range 0 to 999999999
     * @param limit          Max number of invoices per page e.g. 10.
     *                       Range 1 to 999999999.
     * @return The invoices.
     */
    Invoices getInvoices(String accountId, String paymentAddress, List<Invoice.State> state, int offset, int limit);

    /**
     * Get all invoices, filtered.
     *
     * @param accountId      Account that invoices belong to e.g. 821baab2-90e1-a32d-2094-b31da1db6a42.
     * @param state          One or more states to filter Invoices by.
     * @param offset         Start index position for list of entities returned e.g. 5.
     *                       Range 0 to 999999999
     * @param limit          Max number of invoices per page e.g. 10.
     *                       Range 1 to 999999999.
     * @return The invoices.
     */
    Invoices getInvoices(String accountId, List<Invoice.State> state, int offset, int limit);

    /**
     * Get all invoices, filtered.
     *
     * @param transitionedTo One or more states to filter Invoices by.
     * @param transitionedOn Filter invoices that have transitioned during this period
     *                       EXAMPLE: 2014-09-02T00:00:00.000Z..2014-09-03T00:00:00.000Z
     * @param accountId      Account that invoices belong to e.g. 821baab2-90e1-a32d-2094-b31da1db6a42.
     * @param offset         Start index position for list of entities returned e.g. 5.
     *                       Range 0 to 999999999
     * @param limit          Max number of invoices per page e.g. 10.
     *                       Range 1 to 999999999.
     * @return The invoices.
     */
    Invoices getInvoices(List<Invoice.State> transitionedTo, String transitionedOn, String accountId, int offset, int limit);

    /**
     * Create a new Invoice.
     * <p>
     * The *orderId* of a previously created order must be supplied when creating an invoice, along with your Bitnet *accountId*.
     * <p>
     * The order must be in a state of **OPEN**.
     * <p>
     * On successful creation of an invoice, the linked order will be updated to a state of **INVOICED**.
     *
     * @param entity e.g. invoice/invoice-create.json.
     * @return The created invoice.
     */
    Invoice createInvoice(InvoiceCreate entity);

    /**
     * Get invoice.
     *
     * @param invoiceId e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The invoice.
     */
    Invoice getInvoice(String invoiceId);

    /**
     * Update the state of an invoice.
     * <p>
     * The state of an invoice can be updated to **CANCELED** if the invoice is *OPEN* and no payments have been detected.
     *
     * @param invoiceId e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @param entity    e.g. invoice/invoice-update.json.
     * @return The updated invoice.
     */
    Invoice updateInvoice(String invoiceId, InvoiceUpdate entity);
}
