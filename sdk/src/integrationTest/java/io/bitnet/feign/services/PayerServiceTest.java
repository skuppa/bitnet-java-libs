/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.services;

import feign.codec.EncodeException;
import io.bitnet.api.PayerService;
import io.bitnet.core.exceptions.BitnetAccessDeniedException;
import io.bitnet.core.exceptions.BitnetConflictException;
import io.bitnet.core.exceptions.BitnetRequestCouldNotBeProcessedException;
import io.bitnet.core.exceptions.BitnetResourceNotFoundException;
import io.bitnet.feign.TestRequestCounterInterceptor;
import io.bitnet.model.payer.payer.Address;
import io.bitnet.model.payer.payer.Payer;
import io.bitnet.model.payer.payer.PayerCreate;
import io.bitnet.model.payer.payer.PayerUpdate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.bitnet.AssertionHelper.assertIsUUID;
import static io.bitnet.AssertionHelper.assertThatContactNumbersMatch;
import static io.bitnet.TestCredentials.testAccountId;
import static io.bitnet.TestFactory.*;
import static io.bitnet.TestUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

/**
 * Integration tests for {@link io.bitnet.api.PayerService}.
 */
public class PayerServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(PayerServiceTest.class);
    private static final String EMAIL = "payer.1@bitnet.io";
    private PayerService target;

    @Before
    public void setUp() {
        target = newBitnetService().payerService();
    }

    @Test
    public void shouldCreatePayerWithOnlyRequiredInformation() {
        Payer createdPayer = target.createPayer(new PayerCreate()
                .withAccountId(testAccountId())
                .withEmail(EMAIL));

        logger.info("Created Payer: {}", createdPayer);

        assertThat(createdPayer.getEmail(), is(equalTo(EMAIL)));
        assertIsUUID(createdPayer.getId());
    }

    @Test
    public void shouldCreatePayerWithAllInformation() {
        PayerCreate payerToCreate = getPayerCreateWithAllInformation(EMAIL);

        Payer createdPayer = target.createPayer(payerToCreate);

        logger.info("Created Payer: {}", createdPayer);

        assertThat(createdPayer.getEmail(), is(equalTo(EMAIL)));
        assertThat(createdPayer.getFirstName(), is(equalTo(payerToCreate.getFirstName())));
        assertThat(createdPayer.getLastName(), is(equalTo(payerToCreate.getLastName())));
        assertIsUUID(createdPayer.getId());
        assertThat(createdPayer.getAddress(), is(equalTo(payerToCreate.getAddress())));
        assertThatContactNumbersMatch(payerToCreate.getContactNumbers(), createdPayer.getContactNumbers());
        assertThat(createdPayer.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(createdPayer.getRefundPaymentAddress(), is(equalTo(payerToCreate.getRefundPaymentAddress())));
    }

    @Test
    public void shouldGetBitnetConflictExceptionWhenCreatingPayerWhichAlreadyExists() {
        PayerCreate payerToCreate = getPayerCreateWithAllInformation(EMAIL);
        Payer createdPayer = target.createPayer(payerToCreate);
        logger.info("Created Payer: {}", createdPayer);

        try {
            target.createPayer(payerToCreate);
            fail();
        } catch (BitnetConflictException exception) {
            logger.info("Bitnet Conflict Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Payer already exists with reference"));
        }
    }

    @Test
    public void shouldGetBitnetErrorPropertiesWhenLoggingLevelSetToNone() {
        PayerService serviceWithNoLogging = newBitnetServiceWithNoLogging().payerService();
        PayerCreate payerToCreate = getPayerCreateWithAllInformation(EMAIL);
        serviceWithNoLogging.createPayer(payerToCreate);

        try {
            serviceWithNoLogging.createPayer(payerToCreate);
            fail();
        } catch (BitnetConflictException exception) {
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Payer already exists with reference"));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenCreatingPayerWithMissingEmailAddress() {
        PayerCreate payerToCreate = new PayerCreate().withAccountId(testAccountId());

        try {
            target.createPayer(payerToCreate);
            fail();
        } catch (EncodeException exception) {
            logger.info("Bitnet Invalid Request Exception: {}", exception);
            assertThat(exception.getMessage(), containsString("email may not be null"));
        }
    }

    @Test
    public void shouldUpdatePayer() {
        Payer createdPayer = createNewPayer();

        PayerUpdate payerToUpdate = new PayerUpdate(new Payer())
                .withReference(getRandomId())
                .withAddress(createdPayer.getAddress())
                .withFirstName("BillyUpdate")
                .withLastName("UpdatedBob")
                .withEmail("updated@email.com");

        payerToUpdate.getAddress().setAddressLine1("20 new street");
        payerToUpdate.getAddress().setAddressLine2("Another place");
        payerToUpdate.getAddress().setCity("Another city");
        payerToUpdate.getAddress().setCountry(Address.Country.AD);
        payerToUpdate.getAddress().setPostalCode("UPDATEX");
        payerToUpdate.getAddress().setRegion("another region");
        payerToUpdate.setContactNumbers(getContactNumbers("0899112233", "02895383838", "07001231233"));


        Payer updatedPayer = target.updatePayer(payerToUpdate, createdPayer.getId());
        logger.info("Updated payer: {}", updatedPayer);

        assertThat(updatedPayer.getEmail(), is(equalTo(payerToUpdate.getEmail())));
        assertThat(updatedPayer.getReference(), is(equalTo(payerToUpdate.getReference())));
        assertThat(updatedPayer.getFirstName(), is(equalTo(payerToUpdate.getFirstName())));
        assertThat(updatedPayer.getLastName(), is(equalTo(payerToUpdate.getLastName())));
        assertThat(updatedPayer.getAddress(), is(equalTo(payerToUpdate.getAddress())));
        assertThatContactNumbersMatch(updatedPayer.getContactNumbers(), payerToUpdate.getContactNumbers());
        assertThat(updatedPayer.getCreatedAt(), is(not(emptyOrNullString())));
        assertThat(updatedPayer.getModifiedAt(), is(not(emptyOrNullString())));
    }

    @Test
    public void shouldGetBitnetResourceNotFoundExceptionWhenUpdatingPayerWhichDoesNotExist() {
        PayerUpdate payerToUpdate = new PayerUpdate(new Payer()).withEmail("blah@gmail.com");

        try {
            target.updatePayer(payerToUpdate, getRandomId());
            fail();
        } catch (BitnetResourceNotFoundException exception) {
            logger.info("Bitnet Resource Not Found Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
            assertThat(exception.getErrorMessages().get(0), containsString("Unable to find Payer"));
        }
    }

    @Test
    public void shouldGetBitnetAccessDeniedExceptionWhenAccountCredentialsInvalid() {
        try {
            newBitnetServiceWithInvalidClientCredentials(new TestRequestCounterInterceptor()).payerService().createPayer(new PayerCreate()
                    .withAccountId(getRandomId())
                    .withEmail(EMAIL));
            fail();
        } catch (BitnetAccessDeniedException exception) {
            logger.info("Bitnet Access Denied Exception: {}", exception);
            assertThat(exception.getMessage(), is(equalTo("Access denied to Bitnet Service. Check your credentials and permissions for the invoked service.")));
        }
    }

    @Test
    public void shouldRetryAuthenticationRequestWhenBitnetAccessDeniedErrorEncountered() {
        TestRequestCounterInterceptor requestCounterInterceptor = new TestRequestCounterInterceptor();

        try {
            newBitnetServiceWithInvalidClientCredentials(requestCounterInterceptor).payerService().createPayer(new PayerCreate()
                    .withAccountId(getRandomId())
                    .withEmail(EMAIL));
            fail();
        } catch (BitnetAccessDeniedException exception) {
            logger.info("Bitnet Access Denied Exception: {}", exception);
            // Note: This is six times as the call to auth is made twice for both of the calls to payer service.
            assertThat(requestCounterInterceptor.numberOfRequests, is(equalTo(6)));
        }
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedExceptionWhenAccountDetailsInvalid() {
        try {
            target.createPayer(new PayerCreate()
                    .withAccountId(getRandomId())
                    .withEmail(EMAIL));
            fail();
        } catch (BitnetRequestCouldNotBeProcessedException exception) {
            logger.info("Bitnet Request could not be processed Exception: {}", exception);
            assertIsUUID(exception.getCorrelationId());
        }
    }

    private Payer createNewPayer() {
        Payer createdPayer = target.createPayer(getPayerCreateWithAllInformation(EMAIL));
        logger.info("Created Payer: {}", createdPayer);
        return createdPayer;
    }
}
