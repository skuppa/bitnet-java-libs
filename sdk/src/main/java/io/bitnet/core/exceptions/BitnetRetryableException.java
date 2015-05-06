/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import feign.RetryableException;

import java.util.Date;

/**
 * All bitnet retryable exceptions should use this as a base.
 */
public abstract class BitnetRetryableException extends RetryableException {
    private final String correlationId;

    public BitnetRetryableException(String message, Date retryAfter, String correlationId) {
        super(message, retryAfter);
        this.correlationId = correlationId;
    }

    /**
     * This id can be used by bitnet to trace transactions.
     *
     * @return The bitnet correlation id.
     */
    public String getCorrelationId() {
        return correlationId;
    }
}
