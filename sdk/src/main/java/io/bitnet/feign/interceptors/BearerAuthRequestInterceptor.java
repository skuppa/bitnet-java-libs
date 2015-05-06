/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.bitnet.feign.AuthenticationManager;

/**
 * Interceptor for adding OAuth bearer access token as header on requests.
 */
public class BearerAuthRequestInterceptor implements RequestInterceptor {
    private final AuthenticationManager authenticationManager;

    public BearerAuthRequestInterceptor(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        String accessToken = authenticationManager.authenticate();
        template.header("Authorization", buildHeaderValue(accessToken));
    }

    private String buildHeaderValue(String accessToken) {
        return "Bearer " + accessToken;
    }
}
