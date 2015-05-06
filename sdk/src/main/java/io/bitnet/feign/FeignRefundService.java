/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.api.RefundService;
import io.bitnet.model.refund.refund.Refund;
import io.bitnet.model.refund.refund.RefundCreate;
import io.bitnet.model.refund.refund.Refunds;

/**
 * Feign representation of RefundService.
 */
@Headers({"Content-Type: application/json"})
interface FeignRefundService extends RefundService {

    @Override
    @RequestLine("GET v1/refunds?accountId={accountId}&invoiceId={invoiceId}&state={state}&offset={offset}&limit={limit}")
    Refunds getRefunds(@Param("accountId") String accountId, @Param("invoiceId") String invoiceId, @Param("state") Refund.State state, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/refunds?accountId={accountId}&state={state}&offset={offset}&limit={limit}")
    Refunds getRefunds(@Param("accountId") String accountId, @Param("state") Refund.State state, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("GET v1/refunds?accountId={accountId}&transitionedTo={transitionedTo}&transitionedOn={transitionedOn}&offset={offset}&limit={limit}")
    Refunds getRefunds(@Param("transitionedTo") Refund.State transitionedTo,
                       @Param("transitionedOn") String transitionedOn, @Param("accountId") String accountId, @Param("offset") int offset, @Param("limit") int limit);

    @Override
    @RequestLine("POST v1/refunds")
    Refund createRefund(RefundCreate entity);

    @Override
    @RequestLine("GET v1/payers/{refundId}")
    Refund getRefund(@Param("refundId") String refundId);
}
