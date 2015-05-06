/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.api;

import io.bitnet.model.token.Token;

/**
 * Bitnet supports OAuth 2.0 for authenticating all API requests.
 */
public interface OAuthService {

    /**
     * Create a new OAuth access token.
     *
     * This access token can then be used in all subsequent API calls.
     *
     * The access token can be used for the period of time denoted in seconds by the expires_in response field.
     *
     * Use Basic Authentication. Username should be your Bitnet client_id and password should be your secret.
     *
     * Examples which can be used are username: 58342612-39a2-43e8-8ff5-5f0cd376972c password: c1d7c59a-5b31-4743-851c-aba0589ee2f6
     *
     * @param grantType This should be one of (client_credentials).
     * @return The new oAuth access token.
     */
    Token authenticate(String grantType);
}
