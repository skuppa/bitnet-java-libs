/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.encoders;

import feign.codec.Encoder;
import io.bitnet.model.payer.payer.PayerCreate;
import org.junit.Test;

import javax.validation.ValidationException;
import java.io.IOException;

import static io.bitnet.Blockchain.TESTNET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;


/**
 * The integration tests for {@link RequestLogger}.
 */
public class RequestValidatorTest {
    public static final String ACCOUNT_ID = "f436aa6e-5d2f-43d6-9a99-0029a66a02f8";

    private final Encoder encoder = mock(Encoder.class);

    @Test
    public void shouldThrowRegexValidationException() throws IOException, InterruptedException {
        try {
            new RequestValidator(encoder, TESTNET).encode(new PayerCreate().withAccountId(ACCOUNT_ID).withEmail("Bandit"), null, null);
            fail("Should have thrown a validation exception");
        } catch (ValidationException ve) {
            assertThat(ve.getMessage(), is("\n\temail must match " +
                    "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\""));
        }
    }

    @Test
    public void shouldThrowTwoRegexValidationException() throws IOException, InterruptedException {
        try {
            new RequestValidator(encoder, TESTNET).encode(new PayerCreate().withAccountId("no").withEmail("Bandit"), null, null);
            fail("Should have thrown a validation exception");
        } catch (ValidationException ve) {
            assertThat(ve.getMessage(), containsString(
                    "\n\taccountId must match " +
                            "\"^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$\""));
            assertThat(ve.getMessage(), containsString("\n\temail must match " +
                    "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\""));
        }
    }

    @Test
    public void shouldNotThrowRegexValidationException() throws IOException, InterruptedException {
        try {
            new RequestValidator(encoder, TESTNET).encode(new PayerCreate().withAccountId(ACCOUNT_ID).withEmail("Bandit@instil.co"), null, null);
        } catch (ValidationException ve) {
            fail("Should not have thrown a validation exception");
        }
    }

    @Test
    public void shouldThrowSizeValidationException() throws IOException, InterruptedException {
        try {
            new RequestValidator(encoder, TESTNET).encode(new PayerCreate().withAccountId(ACCOUNT_ID).withEmail("Bandit@instil.co").withFirstName("Bandit...................." +
                    "..........................................................................................")
                    , null, null);
            fail("Should have thrown a validation exception");
        } catch (ValidationException ve) {
            assertThat(ve.getMessage(), is("\n\tfirstName size must be between 0 and 60"));
        }
    }

    @Test
    public void shouldThrowNotNullValidationException() throws IOException, InterruptedException {
        try {
            new RequestValidator(encoder, TESTNET).encode(new PayerCreate().withAccountId(ACCOUNT_ID), null, null);
            fail("Should have thrown a validation exception");
        } catch (ValidationException ve) {
            assertThat(ve.getMessage(), is("\n\temail may not be null"));
        }
    }
}
