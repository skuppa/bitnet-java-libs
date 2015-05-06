/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * These errors will be generated for resources which can be classified as being conflicted.  For instance, if an attempt is made to
 * create an entity which already exists.
 */
public class BitnetConflictException extends BitnetException {

    public BitnetConflictException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages, correlationId, responseBody);
    }
}
