/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * This error will be thrown when an http resource returns an invalid request error. Information about the error
 * can be found in the error messages and the full error response body is included. The error message will contain the
 * first of any error messages returned. The Bitnet correlationId can be used by Bitnet to trace this transaction.
 */
public class BitnetInvalidRequestException extends BitnetException {

    public BitnetInvalidRequestException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages, correlationId, responseBody);
    }
}
