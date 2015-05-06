/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * This is the general Bitnet exception. It will be thrown when a more specific error is not identified.
 */
public class BitnetException extends RuntimeException {
    private final List<String> errorMessages;
    private final String correlationId;
    private final String responseBody;

    public BitnetException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages != null && errorMessages.size() > 0 ? errorMessages.get(0) : "An error occurred when using Bitnet services. Check exception properties for more details.");
        this.errorMessages = errorMessages;
        this.correlationId = correlationId;
        this.responseBody = responseBody;
    }

    /**
     * This id can be used by bitnet to trace transactions.
     *
     * @return The bitnet correlation id.
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * This will contain the full http response payload.
     *
     * @return The response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * This will contain all error messages returned from Bitnet.
     *
     * @return The error messages.
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
