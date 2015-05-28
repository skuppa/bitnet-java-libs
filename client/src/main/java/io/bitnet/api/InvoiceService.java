/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.payment.*;

import java.util.List;

/**
* An invoice is the instruction for the payment which is generated for an order. 
* <p>
* An invoice is required to process a payment in digital currency    
*/
public interface InvoiceService {

        /**
        * Get all invoices, optionally filtered.
        *
        * @param accountId Account that invoices belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param orderId Order that invoices belong to eg. 1d001ab9-772f-3942-213d-93144fd851da
        * @param paymentAddress Payment address for a specific invoice eg. mvzssahncsZBXmHWSYk7HdAbVwQR8KRVQk
        * @param reference Reference for a specific invoice eg. Customer-Order-0000-1234-5678
        * @param state Filter invoices by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param transitionedTo Filter invoices that have transitioned to this state during the transitionedOn period. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param transitionedOn Filter invoices that have transitioned during this period eg. 2014-09-02T00:00:00.000Z..2014-09-03T00:00:00.000Z
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Invoices
        */
        Invoices getInvoices(String accountId, String orderId, String paymentAddress, String reference, List<Invoice.State> state, Invoice.State transitionedTo, String transitionedOn, int offset, int limit);

        /**
        * Get all invoices, optionally filtered.
        *
        * @param accountId Account that invoices belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param state Filter invoices by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param orderId Order that invoices belong to eg. 1d001ab9-772f-3942-213d-93144fd851da
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Invoices
        */
        Invoices getInvoices(String accountId, List<Invoice.State> state, String orderId, int offset, int limit);

        /**
        * Get all invoices, optionally filtered.
        *
        * @param accountId Account that invoices belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param paymentAddress Payment address for a specific invoice eg. mvzssahncsZBXmHWSYk7HdAbVwQR8KRVQk
        * @param state Filter invoices by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Invoices
        */
        Invoices getInvoices(String accountId, String paymentAddress, List<Invoice.State> state, int offset, int limit);

        /**
        * Get all invoices, optionally filtered.
        *
        * @param accountId Account that invoices belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param state Filter invoices by current state.  Multiple state params can be supplied to filter by multiple states. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Invoices
        */
        Invoices getInvoices(String accountId, List<Invoice.State> state, int offset, int limit);

        /**
        * Get all invoices, optionally filtered.
        *
        * @param transitionedTo Filter invoices that have transitioned to this state during the transitionedOn period. Is case-sensitive, and must be all UPPERCASE eg. PAID
        * @param transitionedOn Filter invoices that have transitioned during this period eg. 2014-09-02T00:00:00.000Z..2014-09-03T00:00:00.000Z
        * @param accountId Account that invoices belong to eg. 821baab2-90e1-a32d-2094-b31da1db6a42
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Invoices
        */
        Invoices getInvoices(Invoice.State transitionedTo, String transitionedOn, String accountId, int offset, int limit);

        /**
        * Get all invoices, optionally filtered.
        *
        * @param reference Reference for a specific invoice eg. Customer-Order-0000-1234-5678
        *
        * @return The Invoices
        */
        Invoices getInvoices(String reference);

        /**
        * Create a new Invoice.
        * <p>.
        * The *orderId* of a previously created order must be supplied when creating an invoice, along with your Bitnet *accountId*.  .
        * <p>.
        * The order must be in a state of **OPEN**.  .
        * <p>.
        * On successful creation of an invoice, the linked order will be updated to a state of **INVOICED**..
        *
        * @param entity  eg. InvoiceCreate
        *
        * @return The Invoice
        */
        Invoice createInvoice(InvoiceCreate entity);

        /**
        * Get invoice.
        *
        * @param invoiceId  eg. f47ac10b-58cc-4372-a567-5a02b2c3e479
        *
        * @return The Invoice
        */
        Invoice getInvoice(String invoiceId);

        /**
        * Update the state of an invoice.
        * <p>.
        * The state of an invoice can be updated to **CANCELED** if the invoice is *OPEN* and no payments have been detected..
        *
        * @param entity  eg. InvoiceUpdate
        * @param invoiceId  eg. f47ac10b-58cc-4372-a567-5a02b2c3e479
        *
        * @return The Invoice
        */
        Invoice updateInvoice(InvoiceUpdate entity, String invoiceId);

}
