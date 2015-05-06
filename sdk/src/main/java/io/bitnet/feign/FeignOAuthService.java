/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign;


import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.bitnet.api.OAuthService;
import io.bitnet.model.token.Token;

/**
 * Feign representation of OAuthApi.
 */
@Headers({"Content-Type: application/json"})
interface FeignOAuthService extends OAuthService {

    @Override
    @RequestLine("POST v1/oauth/token?grant_type={grantType}")
    Token authenticate(@Param("grantType") String grantType);
}
