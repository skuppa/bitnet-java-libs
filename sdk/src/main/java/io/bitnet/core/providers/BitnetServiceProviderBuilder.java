/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.core.providers;

/**
 * Builder used for configuring and setting up bitnet service providers.
 */
public class BitnetServiceProviderBuilder {
    private final BitnetServiceProvider serviceProvider;

    private BitnetServiceProviderBuilder(BitnetServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public static BitnetServiceProviderBuilder client(BitnetServiceProvider serviceProvider) {
        return new BitnetServiceProviderBuilder(serviceProvider);
    }

    public BitnetServiceProvider get() {
        return serviceProvider;
    }
}
