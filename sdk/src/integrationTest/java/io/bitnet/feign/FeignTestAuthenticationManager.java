/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import io.bitnet.core.cache.DefaultCache;
import io.bitnet.model.token.Token;

/**
 * We need to provide a new version of the authentication manager for tests as we are not implementing the BitnetServiceProvider
 * in the integration tests.
 */
public class FeignTestAuthenticationManager implements AuthenticationManager {
    private final FeignTestServiceProvider bitnetServiceProvider;
    private final DefaultCache cache;

    public FeignTestAuthenticationManager(FeignTestServiceProvider bitnetServiceProvider, DefaultCache cache) {
        this.cache = cache;
        this.bitnetServiceProvider = bitnetServiceProvider;
    }

    @Override
    public synchronized void resetAuthenticationStatus() {
        cache.removeAccessToken();
    }

    @Override
    public synchronized String authenticate() {
        Token accessToken = cache.getAccessToken();
        if (accessToken == null) {
            Token newToken = bitnetServiceProvider.oAuthService().authenticate("client_credentials");
            cache.storeAccessToken(newToken);
            return newToken.getAccessToken();
        }

        return accessToken.getAccessToken();
    }
}
