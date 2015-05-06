/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payer.payer.PayerUpdate;

/**
 * A payer is a representation of a consumer, containing contact details etc.
 * <p>
 * A payer also contains a refund payment address, which can be populated at time of creation, or updated at a later date.
 * <p>
 * A refund payment address must be set in order to initiate a refund.
 */
public interface PayerService {

    /**
     * Create a new payer.
     * <p>
     * A payer must include an e-mail address on creation, as well as your Bitnet *accountId*.
     * <p>
     * An optional unique reference may also be supplied when creating a payer. If a payer already exists with the same reference, a link to the existing payer will be returned in the response.
     * <p>
     * A payer also contains a refund payment address, which can be populated at time of creation, or updated at a later date.  A refund payment address must be set in order to initiate a refund.
     *
     * @param entity e.g. payer/payer-create.json.
     * @return The created payer.
     */
    Payer createPayer(PayerCreate entity);

    /**
     * Get payer.
     *
     * @param payerId e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The payer.
     */
    Payer getPayer(String payerId);

    /**
     * Update an existing payer.
     * <p>
     * All payer information can be updated.
     * <p>
     * To update a payer, all previous payer information must be supplied, otherwise this payer information will be overwritten.
     *
     * @param entity  e.g. payer/payer-update.json.
     * @param payerId e.g. f47ac10b-58cc-4372-a567-5a02b2c3e479.
     * @return The updated payer.
     */
    Payer updatePayer(PayerUpdate entity, String payerId);
}
