/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * These errors will be thrown when a requested resource is invalid or does not exist.
 */
public class BitnetResourceNotFoundException extends BitnetException {

    public BitnetResourceNotFoundException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages, correlationId, responseBody);
    }
}
