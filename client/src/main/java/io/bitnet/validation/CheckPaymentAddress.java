/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for checking payment addresses.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PaymentAddressValidator.class)
@Documented
public @interface CheckPaymentAddress {
    String message() default "is not valid for configured bitcoin blockchain ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


