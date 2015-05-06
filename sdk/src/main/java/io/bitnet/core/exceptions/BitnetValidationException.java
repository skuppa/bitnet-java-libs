/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.core.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * This will be thrown when building a request to send to the Bitnet Service. These exceptions may occur as a result of
 * the passed object not adhering to its constraints. Common issues are missing fields, invalid contents etc.
 */
public class BitnetValidationException extends ValidationException {
    private final Set<ConstraintViolation<Object>> constraintViolations;

    public BitnetValidationException(String message, Set<ConstraintViolation<Object>> constraintViolations) {
        super(message);
        this.constraintViolations = constraintViolations;
    }

    public Set<ConstraintViolation<Object>> getConstraintViolations() {
        return constraintViolations;
    }
}
