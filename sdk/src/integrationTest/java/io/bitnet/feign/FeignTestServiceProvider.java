/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.bitnet.api.OAuthService;
import io.bitnet.core.cache.DefaultCache;
import io.bitnet.feign.interceptors.BearerAuthRequestInterceptor;

import static io.bitnet.TestCredentials.testEnvironment;

/**
 * The integration tests require the use of some internal Bitnet functions, such as setting invoices to paid.
 * This service provider gives a way of accessing those functions.
 */
public class FeignTestServiceProvider {
    private final String clientId;
    private final String secret;

    private FeignTestInvoiceService invoiceService;
    private FeignOAuthService oAuthService;
    private DefaultCache cache;
    private FeignTestAuthenticationManager authenticationManager;

    public FeignTestServiceProvider(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
        buildDependencies();
        invoiceService = invoiceService();
        oAuthService = (FeignOAuthService) oAuthService();
    }

    private void buildDependencies() {
        cache = new DefaultCache();
        authenticationManager = new FeignTestAuthenticationManager(this, cache);
    }

    protected FeignTestAuthenticationManager authenticationManager() {
        return authenticationManager;
    }

    public OAuthService oAuthService() {
        return getServiceUsingBasicAuth(oAuthService, FeignOAuthService.class);
    }

    public FeignTestInvoiceService invoiceService() {
        return getServiceUsingBearerAuth(invoiceService, FeignTestInvoiceService.class);
    }

    private <T> T getServiceUsingBasicAuth(T service, Class<T> serviceInterface) {
        if (service == null) {
            service = feignBuilderWithBasicAuth().target(serviceInterface, testEnvironment());
        }
        return service;
    }

    private <T> T getServiceUsingBearerAuth(T service, Class<T> serviceInterface) {
        if (service == null) {
            service = feignBuilderWithBearerAuth().target(serviceInterface, testEnvironment());
        }
        return service;
    }

    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .client(OkHttpClientProvider.unsafeOkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder());
    }

    private Feign.Builder feignBuilderWithBasicAuth() {
        return feignBuilder().requestInterceptor(new BasicAuthRequestInterceptor(clientId, secret));
    }

    private Feign.Builder feignBuilderWithBearerAuth() {
        return feignBuilder().requestInterceptor(new BearerAuthRequestInterceptor(authenticationManager()));
    }
}
