/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * This allows us to count the number of requests made in testing.
 */
public class TestRequestCounterInterceptor implements RequestInterceptor {
    public int numberOfRequests = 0;

    @Override
    public void apply(RequestTemplate template) {
        numberOfRequests++;
    }
}
