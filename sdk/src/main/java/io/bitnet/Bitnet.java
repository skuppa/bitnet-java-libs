/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet;

import feign.Logger;
import feign.okhttp.OkHttpClient;
import io.bitnet.api.*;
import io.bitnet.core.providers.BitnetServiceProvider;
import io.bitnet.core.providers.BitnetServiceProviderBuilder;
import io.bitnet.feign.FeignServiceProvider;
import io.bitnet.feign.OkHttpClientProvider;

import static io.bitnet.Blockchain.BITCOIN;
import static io.bitnet.Blockchain.TESTNET;

/**
 * Accessor to bitnet API.
 */
public class Bitnet implements BitnetServiceProvider {
    private static final String ENDPOINT = "https://api.bitnet.io/";
    private static final String TEST_ENDPOINT = "https://test-api.bitnet.io/";
    private final BitnetServiceProvider serviceProvider;

    private Bitnet(BitnetServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    /**
     * Instantiate bitnet instance for communicating with the Production Bitnet API.
     *
     * @param clientId merchants client id
     * @param secret   merchants secret
     * @return bitnet configured for accessing production
     */
    public static Bitnet start(String clientId, String secret) {
        return new Bitnet(BitnetServiceProviderBuilder.client(new FeignServiceProvider(clientId, secret, ENDPOINT, BITCOIN)).get());
    }

    /**
     * Instantiate bitnet instance for communicating with the Test Bitnet API.
     *
     * @param clientId merchants client id
     * @param secret   merchants secret
     * @return bitnet configured for accessing test
     */
    public static Bitnet startTest(String clientId, String secret) {
        return new Bitnet(BitnetServiceProviderBuilder.client(new FeignServiceProvider(clientId, secret, TEST_ENDPOINT, TESTNET)).get());
    }

    /**
     * Instantiate bitnet instance for communicating with the Test Bitnet API.
     *
     * @param clientId merchants client id
     * @param secret   merchants secret
     * @param loggingLevel    This will log key information and all request / response data. Caution: Personally Identifying Data will be logged so only use with test data.
     * @return bitnet configured for accessing test
     */
    public static Bitnet startTest(String clientId, String secret, Logger.Level loggingLevel) {
        return new Bitnet(BitnetServiceProviderBuilder.client(new FeignServiceProvider(clientId, secret, TEST_ENDPOINT, TESTNET, loggingLevel, OkHttpClientProvider.unsafeOkHttpClient())).get());
    }

    /**
     * Instantiate bitnet instance for communicating with a custom bitnet endpoint and blockchain.
     * This can be used to work with test environments.
     *
     * @param clientId   merchants client id
     * @param secret     merchants secret
     * @param endpoint   endpoint of bitnet environment
     * @param blockchain blockchain used by the bitnet environment
     * @param loggingLevel  This will log key information and all request / response data. Caution: Personally Identifying Data will be logged so only use with test data.
     * @param client This will configure the client used to communicate with the Bitnet API. See {@link io.bitnet.feign.OkHttpClientProvider} for pre-configured clients.
     * @return bitnet configured for accessing a custom bitnet environment
     */
    public static Bitnet start(String clientId, String secret, String endpoint, Blockchain blockchain, Logger.Level loggingLevel, OkHttpClient client) {
        return new Bitnet(BitnetServiceProviderBuilder.client(new FeignServiceProvider(clientId, secret, endpoint, blockchain, loggingLevel, client)).get());
    }

    /**
     * Instantiate bitnet instance using customized service provider.
     *
     * @param serviceProvider a custom service provider
     * @return bitnet configured using custom service provider
     */
    public static Bitnet start(BitnetServiceProvider serviceProvider) {
        return new Bitnet(serviceProvider);
    }


    @Override
    public InvoiceService invoiceService() {
        return serviceProvider.invoiceService();
    }

    @Override
    public OrderService orderService() {
        return serviceProvider.orderService();
    }


    @Override
    public PayerService payerService() {
        return serviceProvider.payerService();
    }


    @Override
    public RefundService refundService() {
        return serviceProvider.refundService();
    }
}
