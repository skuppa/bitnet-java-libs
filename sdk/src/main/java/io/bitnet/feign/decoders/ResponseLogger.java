/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.decoders;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Decorator which logs decoded object from responses.
 */
public class ResponseLogger implements Decoder {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseLogger.class);
    private final Decoder decoder;

    public ResponseLogger(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        Object decodedObject = decoder.decode(response, type);
        LOG.info("Response body: {}, correlationId: {}", decodedObject, response.headers().get("Correlation-Id"));
        return decodedObject;
    }
}
