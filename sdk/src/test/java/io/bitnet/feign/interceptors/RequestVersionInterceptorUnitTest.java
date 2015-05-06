/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.interceptors;

import feign.RequestTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * The unit tests for {@link BearerAuthRequestInterceptor}.
 */
public class RequestVersionInterceptorUnitTest {

    private RequestVersionInterceptor target;
    private RequestTemplate requestTemplate;

    @Before
    public void setUp() {
        requestTemplate = new RequestTemplate();
        target = new RequestVersionInterceptor();
    }

    @Test
    public void shouldAuthenticateAndAddAccessTokenToHeaders() {
        target.apply(requestTemplate);

        Object[] headers = requestTemplate.headers().get("User-Agent").toArray();
        assertThat("bitnet-java-sdk@2.5.0", is(equalTo(headers[0].toString())));
    }
}
