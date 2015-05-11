/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign;

import feign.Feign;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.bitnet.Blockchain;
import io.bitnet.api.*;
import io.bitnet.core.cache.DefaultCache;
import io.bitnet.core.providers.BitnetServiceProvider;
import io.bitnet.feign.decoders.*;
import io.bitnet.feign.encoders.RequestLogger;
import io.bitnet.feign.encoders.RequestValidator;
import io.bitnet.feign.interceptors.BearerAuthRequestInterceptor;
import io.bitnet.feign.interceptors.RequestVersionInterceptor;


/**
 * Bitnet service provider implemented using feign.
 */
public class FeignServiceProvider implements BitnetServiceProvider {

    private final String clientId;
    private final String secret;
    private final String endPoint;
    private final Blockchain blockchain;
    private final OkHttpClient client;

    private final Logger.Level loggingLevel;
    private FeignInvoiceService invoiceService;
    private FeignOAuthService oAuthService;
    private FeignOrderService orderService;
    private FeignPayerService payerService;
    private FeignRefundService refundService;
    private DefaultCache cache;
    protected FeignAuthenticationManager authenticationManager;

    public FeignServiceProvider(String clientId, String secret, String endPoint, Blockchain blockchain) {
        this(clientId, secret, endPoint, blockchain, Logger.Level.NONE, OkHttpClientProvider.okHttpClient());
    }

    public FeignServiceProvider(String clientId, String secret, String endPoint, Blockchain blockchain, Logger.Level loggingLevel, OkHttpClient client) {
        this.clientId = clientId;
        this.secret = secret;
        this.endPoint = endPoint;
        this.blockchain = blockchain;
        this.loggingLevel = loggingLevel;
        this.client = client;
        buildDependencies();
    }



    private void buildDependencies() {
        cache = new DefaultCache();
        authenticationManager = new FeignAuthenticationManager(this, cache);
    }

    protected AuthenticationManager authenticationManager() {
        return authenticationManager;
    }

    public OAuthService oAuthService() {
        return getServiceUsingBasicAuth(oAuthService, FeignOAuthService.class);
    }

    @Override
    public InvoiceService invoiceService() {
        return getServiceUsingBearerAuth(invoiceService, FeignInvoiceService.class);
    }

    @Override
    public OrderService orderService() {
        return getServiceUsingBearerAuth(orderService, FeignOrderService.class);
    }

    @Override
    public PayerService payerService() {
        return getServiceUsingBearerAuth(payerService, FeignPayerService.class);
    }

    @Override
    public RefundService refundService() {
        return getServiceUsingBearerAuth(refundService, FeignRefundService.class);
    }

    private <T> T getServiceUsingBasicAuth(T service, Class<T> serviceInterface) {
        if (service == null) {
            service = feignBuilderWithBasicAuth().target(serviceInterface, endPoint);
        }
        return service;
    }

    private <T> T getServiceUsingBearerAuth(T service, Class<T> serviceInterface) {
        if (service == null) {
            service = feignBuilderWithBearerAuth().target(serviceInterface, endPoint);
        }
        return service;
    }

    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .client(client)
                .logger(new Slf4jLogger())
                .logLevel(loggingLevel)
                .encoder(new RequestLogger(new RequestValidator(new JacksonEncoder(), blockchain)))
                .decoder(new ResponseLogger(new JacksonDecoder()))
                .errorDecoder(new BitnetErrorDecoder(new BitnetErrorFactory(new BitnetErrorResponseInterrogator()), authenticationManager()))
                .retryer(new ConfigurableRetryer())
                .requestInterceptor(new RequestVersionInterceptor());
    }

    private Feign.Builder feignBuilderWithBasicAuth() {
        return feignBuilder().requestInterceptor(new BasicAuthRequestInterceptor(clientId, secret));
    }

    private Feign.Builder feignBuilderWithBearerAuth() {
        return feignBuilder().requestInterceptor(new BearerAuthRequestInterceptor(authenticationManager()));
    }

    public Blockchain blockchain() {
        return blockchain;
    }
}
