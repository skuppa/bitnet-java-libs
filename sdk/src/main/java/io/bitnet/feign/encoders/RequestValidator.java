/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.encoders;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import io.bitnet.Blockchain;
import io.bitnet.core.exceptions.BitnetValidationException;
import io.bitnet.validation.PaymentAddressValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;

import javax.validation.*;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Decorator which validates object adheres to constraints.
 */
public class RequestValidator implements Encoder {
    private final Encoder encoder;
    private final ValidatorFactory validatorFactory;

    public RequestValidator(final Encoder encoder, final Blockchain blockchain) {
        this.encoder = encoder;

        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        this.validatorFactory = configuration
                .constraintValidatorFactory(new ConstraintValidatorFactory() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
                        if (key == PaymentAddressValidator.class) {
                            return (T) new PaymentAddressValidator(blockchain);
                        }
                        return new ConstraintValidatorFactoryImpl().getInstance(key);
                    }

                    @Override
                    public void releaseInstance(ConstraintValidator<?, ?> instance) {
                    }
                }).buildValidatorFactory();
    }


    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        Set<ConstraintViolation<Object>> constraintViolations = validatorFactory.getValidator().validate(object);

        if (hasConstraintViolations(constraintViolations)) {
            throw buildValidationException(constraintViolations);
        }

        encoder.encode(object, bodyType, template);
    }

    private BitnetValidationException buildValidationException(Set<ConstraintViolation<Object>> constraintViolations) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            builder.append("\n\t").append(constraintViolation.getPropertyPath()).append(" ").append(constraintViolation.getMessage());
        }
        return new BitnetValidationException(builder.toString(), constraintViolations);
    }

    private boolean hasConstraintViolations(Set<ConstraintViolation<Object>> constraintViolations) {
        return constraintViolations.size() > 0;
    }
}
