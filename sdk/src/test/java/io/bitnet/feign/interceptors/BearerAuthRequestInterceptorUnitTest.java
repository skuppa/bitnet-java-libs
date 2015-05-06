/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.interceptors;

import feign.RequestTemplate;
import io.bitnet.feign.FeignAuthenticationManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit tests for {@link io.bitnet.feign.interceptors.BearerAuthRequestInterceptor}.
 */
public class BearerAuthRequestInterceptorUnitTest {

    private BearerAuthRequestInterceptor target;
    private FeignAuthenticationManager authenticationManager;
    private RequestTemplate requestTemplate;

    @Before
    public void setUp() {
        requestTemplate = new RequestTemplate();
        authenticationManager = mock(FeignAuthenticationManager.class);
        target = new BearerAuthRequestInterceptor(authenticationManager);
    }

    @Test
    public void shouldAuthenticateAndAddAccessTokenToHeaders() {
        when(authenticationManager.authenticate()).thenReturn("accessToken");

        target.apply(requestTemplate);

        Object[] headers = requestTemplate.headers().get("Authorization").toArray();
        assertThat("Bearer accessToken", is(equalTo(headers[0].toString())));
    }
}
