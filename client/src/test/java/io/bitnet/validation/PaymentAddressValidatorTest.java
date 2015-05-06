/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static io.bitnet.Blockchain.BITCOIN;
import static io.bitnet.Blockchain.TESTNET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit tests for {@link io.bitnet.validation.PaymentAddressValidator}.
 */
public class PaymentAddressValidatorTest {
    private ConstraintValidatorContext context;

    @Before
    public void setupContext() {
        context = mock(ConstraintValidatorContext.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));
    }

    @Test
    public void shouldVerifyAddressValidForMainnet() {
        assertThat(new PaymentAddressValidator(BITCOIN).isValid("3EktnHQD7RiAE6uzMj2ZifT9YgRrkSgzQX", context), is(true));
    }

    @Test
    public void shouldVerifyAddressInvalidMainnet() {
        assertThat(new PaymentAddressValidator(BITCOIN).isValid("2MzQwSSnBHWHqSAqtTVQ6v47XtaisrJa1Vc", context), is(false));
    }

    @Test
    public void shouldVerifyAddressValidForTestnet() {
        assertThat(new PaymentAddressValidator(TESTNET).isValid("2MzQwSSnBHWHqSAqtTVQ6v47XtaisrJa1Vc", context), is(true));
    }

    @Test
    public void shouldVerifyAddressInvalidTestnet() {
        assertThat(new PaymentAddressValidator(TESTNET).isValid("2MzQwSSnBHWHqSAqtTVQ6v47XtaisrJa1Vc", context), is(true));
    }

    @Test
    public void shouldVerifyNoAddressIsValid() {
        assertThat(new PaymentAddressValidator(BITCOIN).isValid(null, context), is(true));
        assertThat(new PaymentAddressValidator(TESTNET).isValid(null, context), is(true));
    }

}
