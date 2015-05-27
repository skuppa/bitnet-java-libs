/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.bitnet.model.token.Token;

import java.util.concurrent.TimeUnit;

/**
 * This is the default accessTokenCache for the client library.
 */
public class DefaultCache {
    private Cache<String, Token> accessTokenCache;
    private static final String ACCESS_TOKEN = "accessToken";

    /**
     * This will cache the access token according to the expiry time specified by the token.
     *
     * @param accessToken The access token to be cached.
     */
    public void storeAccessToken(Token accessToken) {
        accessTokenCache = CacheBuilder.newBuilder()
                .expireAfterWrite(accessToken.getExpiresIn().longValue(), TimeUnit.SECONDS)
                .build();

        accessTokenCache.put(ACCESS_TOKEN, accessToken);
    }

    /**
     * Get the cached token when available.
     *
     * @return The token or null if no token to return.
     */
    public Token getAccessToken() {
        return accessTokenCache != null ? accessTokenCache.getIfPresent(ACCESS_TOKEN) : null;
    }

    /**
     * Remove the access token from the cache.
     */
    public void removeAccessToken() {
        if (accessTokenCache != null) {
            accessTokenCache.invalidate(ACCESS_TOKEN);
        }
    }
}
