/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

/**
 * This describes the functions used when authenticating.
 */
public interface AuthenticationManager {
    /**
     * Allows for authentication to be reset which should force re-authentication.
     */
    void resetAuthenticationStatus();

    /**
     * Authenticate.
     * @return Get the auth token.
     */
    String authenticate();
}
