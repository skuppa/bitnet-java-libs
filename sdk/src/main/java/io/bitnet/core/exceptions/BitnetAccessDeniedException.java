/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import java.util.Date;

/**
 * This will be thrown after an access denied error is received from a Bitnet service.  These exceptions may occur as a result
 * of service authentication credentials expiring; in these instances we will re-authenticate and retry the call to the service.
 */
public class BitnetAccessDeniedException extends BitnetRetryableException {

    public BitnetAccessDeniedException(String message, Date retryAfter, String correlationId) {
        super(message, retryAfter, correlationId);
    }
}
