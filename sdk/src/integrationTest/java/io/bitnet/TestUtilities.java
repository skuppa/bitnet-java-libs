/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import io.bitnet.feign.ConfigurableRetryer;
import io.bitnet.feign.FeignServiceProvider;
import io.bitnet.feign.OkHttpClientProvider;
import io.bitnet.feign.TestRequestCounterInterceptor;
import io.bitnet.feign.decoders.BitnetErrorDecoder;
import io.bitnet.feign.decoders.BitnetErrorFactory;
import io.bitnet.feign.decoders.BitnetErrorResponseInterrogator;
import io.bitnet.feign.decoders.ResponseLogger;
import io.bitnet.feign.encoders.RequestLogger;
import io.bitnet.feign.encoders.RequestValidator;

import static io.bitnet.Blockchain.TESTNET;
import static io.bitnet.TestCredentials.testClientId;
import static io.bitnet.TestCredentials.testEnvironment;
import static io.bitnet.TestCredentials.testSecret;
import static io.bitnet.TestFactory.getRandomId;

/**
 * Utilities for setting up and configuring tests.
 */
public class TestUtilities {

    public static Bitnet newBitnetService() {
        return Bitnet.start(testClientId(), testSecret(), testEnvironment(), TESTNET, Logger.Level.FULL, OkHttpClientProvider.unsafeOkHttpClient());
    }

    public static Bitnet newBitnetServiceWithNoLogging() {
        return Bitnet.start(testClientId(), testSecret(), testEnvironment(), TESTNET, Logger.Level.NONE, OkHttpClientProvider.unsafeOkHttpClient());
    }

    public static Bitnet newBitnetServiceWithInvalidClientCredentials(final TestRequestCounterInterceptor requestCounterInterceptor) {
        return Bitnet.start(new FeignServiceProvider(getRandomId(), getRandomId(), testEnvironment(), TESTNET) {
            public Feign.Builder feignBuilder() {
                return Feign.builder()
                        .client(OkHttpClientProvider.unsafeOkHttpClient())
                        .logger(new Slf4jLogger())
                        .logLevel(feign.Logger.Level.FULL)
                        .requestInterceptor(requestCounterInterceptor)
                        .encoder(new RequestLogger(new RequestValidator(new JacksonEncoder(), blockchain())))
                        .decoder(new ResponseLogger(new JacksonDecoder()))
                        .errorDecoder(new BitnetErrorDecoder(new BitnetErrorFactory(new BitnetErrorResponseInterrogator()), authenticationManager()))
                        .retryer(new ConfigurableRetryer());
            }
        });
    }

    public static Bitnet newBitnetService(Blockchain environment) {
        return Bitnet.start(testClientId(), testSecret(), testEnvironment(), environment, Logger.Level.FULL, OkHttpClientProvider.unsafeOkHttpClient());
    }
}
