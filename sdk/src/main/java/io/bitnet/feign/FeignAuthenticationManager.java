/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import io.bitnet.core.cache.DefaultCache;
import io.bitnet.model.token.Token;

/**
 * This is responsible for managing the authentication with Bitnet Services for the feign implementation.
 */
public class FeignAuthenticationManager implements AuthenticationManager {
    private final FeignServiceProvider bitnetServiceProvider;
    private final DefaultCache cache;

    public FeignAuthenticationManager(FeignServiceProvider bitnetServiceProvider, DefaultCache cache) {
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
            return newToken.getAccess_token();
        }

        return accessToken.getAccess_token();
    }
}
