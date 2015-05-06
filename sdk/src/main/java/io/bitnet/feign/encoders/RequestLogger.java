/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.encoders;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Decorator which logs object from requests.
 */
public class RequestLogger implements Encoder {
    private static final Logger LOG = LoggerFactory.getLogger(RequestLogger.class);
    private final Encoder encoder;

    public RequestLogger(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        LOG.info("Request Body: {}", object);
        encoder.encode(object, bodyType, template);
    }
}
