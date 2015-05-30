/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.refund.*;

import java.util.List;

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
* <p>
* The Bitnet interface has a number of optional parameters for fetching Refunds. These are handled with overloaded methods.
* The params are: accountId, invoiceId, state, transitionedTo, transitionedOn, offset, limit
*/
public interface RefundService {

        /**
        * Retrieve a list of refunds with optional filters..
        *
        * @param accountId Search for refunds issued against a particular account. eg. c5582ab9-b4f4-421a-8dee-372687a14c26
        * @param invoiceId Filter refunds on an account by invoice. eg. c474e3a1-3664-4c69-b2a4-ed6e9a537301
        * @param state Filter refunds on an account/invoice by current state. eg. PAID
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Refunds
        */
        Refunds getRefunds(String accountId, String invoiceId, Refund.State state, int offset, int limit);

        /**
        * Retrieve a list of refunds with optional filters..
        *
        * @param accountId Search for refunds issued against a particular account. eg. c5582ab9-b4f4-421a-8dee-372687a14c26
        * @param state Filter refunds on an account/invoice by current state. eg. PAID
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Refunds
        */
        Refunds getRefunds(String accountId, Refund.State state, int offset, int limit);

        /**
        * Retrieve a list of refunds with optional filters..
        *
        * @param transitionedTo Filter refunds that have transitioned to this state during the transitionedOn period eg. PAID
        * @param transitionedOn Filter refunds that have transitioned during this period. startDate is inclusive, endDate is exclusive. eg. 2014-09-02T00:00:00.000Z..2014-09-03T00:00:00.000Z
        * @param accountId Search for refunds issued against a particular account. eg. c5582ab9-b4f4-421a-8dee-372687a14c26
        * @param offset Start index position for list of entities returned eg. 5
        * @param limit Max number of profiles per page eg. 10
        *
        * @return The Refunds
        */
        Refunds getRefunds(Refund.State transitionedTo, String transitionedOn, String accountId, int offset, int limit);

        /**
        * You can initiate either a full refund or partial refund. .
        * <p>.
        * The invoice must be in a state of PAID or OVERPAID and the payer associated with the order must have a refund payment address present before a refund can be created..
        * <p>.
        * If you initiate a partial refund, you must specify the amount of money and the currency to refund.             .
        * <p>.
        * For OVERPAID invoices a mispayment correction can be initiated to return the overpaid amount..
        * <p>.
        * <br>.
        * *FULL REFUND*.
        * <p>.
        * To initiate a full refund, an invoiceId and instruction of FULL must be supplied, along with your Bitnet accountId..
        * <p>.
        * <br>.
        * *PARTIAL REFUND*.
        * <p>.
        * To initiate a partial refund, an invoiceId, an instruction of PARTIAL, currency and amount must be supplied, along with your Bitnet accountId. .
        * <p>.
        * The currency supplied in the refund request must match the order pricing currency. .
        * <p>.
        * The refund amount also must be less than the pricing amount of the order..
        * <p>.
        * <br>.
        * *MISPAYMENT CORRECTION*.
        * <p>.
        * To initiate a mispayment correction, an invoiceId and instruction of MISPAYMENT_CORRECTION must be supplied, along with your Bitnet accountId..
        *
        * @param entity  eg. RefundCreate
        *
        * @return The Refund
        */
        Refund createRefund(RefundCreate entity);

        /**
        * Retrieve a refund..
        * <p>.
        * As refunds are asynchronous the actual refund execution may not have taken place yet..
        * <p>.
        * Before refund execution the *sent* attribute of the response will contain a null..
        * <p>.
        * After refund execution the *sent* attribute of the response will contain the amount that was sent to the consumers refund address..
        *
        * @param refundId Unique identifier for the Refund eg. f47ac10b-58cc-4372-a567-5a02b2c3e479
        *
        * @return The Refund
        */
        Refund getRefund(String refundId);

}
