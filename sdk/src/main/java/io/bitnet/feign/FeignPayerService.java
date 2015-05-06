/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.api.PayerService;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payer.payer.PayerUpdate;

/**
 * Feign representation of PayerService.
 */
@Headers({"Content-Type: application/json"})
interface FeignPayerService extends PayerService {

    @Override
    @RequestLine("POST v1/payers")
    Payer createPayer(PayerCreate entity);

    @Override
    @RequestLine("GET v1/payers/{payerId}")
    Payer getPayer(@Param("payerId") String payerId);


    @Override
    @RequestLine("PUT v1/payers/{payerId}")
    Payer updatePayer(PayerUpdate entity, @Param("payerId") String payerId);
}
