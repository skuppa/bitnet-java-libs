/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.List;

/**
 * These will be returned when a request is forbidden.  For instance, if creating a Payer with an invalid Account Id.
 */
public class BitnetRequestForbiddenException extends BitnetException {

    public BitnetRequestForbiddenException(List<String> errorMessages, String correlationId, String responseBody) {
        super(errorMessages, correlationId, responseBody);
    }
}
