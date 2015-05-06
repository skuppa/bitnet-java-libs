/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.refund.refund.Refund;
import io.bitnet.model.refund.refund.RefundCreate;
import io.bitnet.model.refund.refund.Refunds;

/**
 * Refunds are used to refund digital currency to a consumers refund address.
 * <p>
 * There are 3 types of refunds.
 * <p>
 * FULL REFUND
 * <p>
 * PARTIAL REFUND
 * <p>
 * MISPAYMENT CORRECTION
 */
public interface RefundService {

    /**
     * Get all refunds, filtered.
     *
     * @param accountId Search for refunds issued against a particular account. e.g. c5582ab9-b4f4-421a-8dee-372687a14c26.
     * @param invoiceId Filter refunds on an account by invoice. e.g. c474e3a1-3664-4c69-b2a4-ed6e9a537301.
     * @param state     Filter refunds on an account/invoice by state. e.g. PAID.
     * @param offset    Start index position for list of entities returned e.g. 5.
     *                  Range 0 to 999999999.
     * @param limit     Max number of profiles per page e.g. 10.
     *                  Range 1 to 999999999.
     * @return The refunds.
     */
    Refunds getRefunds(String accountId, String invoiceId, Refund.State state, int offset, int limit);

    /**
     * Get all refunds, filtered.
     *
     * @param accountId Search for refunds issued against a particular account. e.g. c5582ab9-b4f4-421a-8dee-372687a14c26.
     * @param state     Filter refunds on an account/invoice by state. e.g. PAID.
     * @param offset    Start index position for list of entities returned e.g. 5.
     *                  Range 0 to 999999999.
     * @param limit     Max number of profiles per page e.g. 10.
     *                  Range 1 to 999999999.
     * @return The refunds.
     */
    Refunds getRefunds(String accountId, Refund.State state, int offset, int limit);

    /**
     * Get all refunds, filtered.
     *
     * @param transitionedTo    State to filter Refunds by.
     * @param transitionedOn    Filter refunds that have transitioned during this period
     *                          EXAMPLE: 2014-09-02T00:00:00.000Z..2014-09-03T00:00:00.000Z
     * @param accountId         Search for refunds issued against a particular account. e.g. c5582ab9-b4f4-421a-8dee-372687a14c26.
     * @param offset            Start index position for list of entities returned e.g. 5.
     *                          Range 0 to 999999999.
     * @param limit             Max number of profiles per page e.g. 10.
     *                          Range 1 to 999999999.
     * @return The refunds.
     */
    Refunds getRefunds(Refund.State transitionedTo, String transitionedOn, String accountId, int offset, int limit);

    /**
     * You can initiate either a full refund or partial refund.
     * <p>
     * The invoice must be in a state of PAID or OVERPAID and the payer associated with the order must have a refund payment address present before a refund can be created.
     * <p>
     * If you initiate a partial refund, you must specify the amount of money and the currency to refund.
     * <p>
     * For OVERPAID invoices a mispayment correction can be initiated to return the overpaid amount.
     * <p>
     * <br>
     * *FULL REFUND*
     * <p>
     * To initiate a full refund, an invoiceId and instruction of FULL must be supplied, along with your Bitnet accountId.
     * <p>
     * <br>
     * *PARTIAL REFUND*
     * <p>
     * To initiate a partial refund, an invoiceId, an instruction of PARTIAL, currency and amount must be supplied, along with your Bitnet accountId.
     * <p>
     * The currency supplied in the refund request must match the order pricing currency.
     * <p>
     * The refund amount also must be less than the pricing amount of the order.
     * <p>
     * <br>
     * *MISPAYMENT CORRECTION*
     * <p>
     * To initiate a mispayment correction, an invoiceId and instruction of MISPAYMENT_CORRECTION must be supplied, along with your Bitnet accountId.
     *
     * @param entity e.g. refund/refund-create.json
     * @return The created refund.
     */
    Refund createRefund(RefundCreate entity);

    /**
     * Retrieve a refund.
     * <p>
     * As refunds are asynchronous the actual refund execution may not have taken place yet.
     * <p>
     * Before refund execution the *sent* attribute of the response will contain a null.
     * <p>
     * After refund execution the *sent* attribute of the response will contain the amount that was sent to the consumers refund address.
     *
     * @param refundId Unique identifier for the Refund e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The refund.
     */
    Refund getRefund(String refundId);
}
