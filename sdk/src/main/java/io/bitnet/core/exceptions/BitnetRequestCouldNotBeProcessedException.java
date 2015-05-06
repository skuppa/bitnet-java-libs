/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * This will be thrown when an error cannot be processed due things like missing fields, field validation, etc.
 */
public class BitnetRequestCouldNotBeProcessedException extends BitnetException {

    public BitnetRequestCouldNotBeProcessedException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages, correlationId, responseBody);
    }
}
