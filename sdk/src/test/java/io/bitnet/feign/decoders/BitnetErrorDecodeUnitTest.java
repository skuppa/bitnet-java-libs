/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

import feign.Response;
import io.bitnet.core.exceptions.*;
import io.bitnet.feign.FeignAuthenticationManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * The unit tests for {@link io.bitnet.feign.decoders.BitnetErrorDecoder}.
 */
public class BitnetErrorDecodeUnitTest {

    private BitnetErrorDecoder target;
    private BitnetErrorFactory errorFactory;
    private FeignAuthenticationManager authenticationManager;

    @Before
    public void setUp() {
        errorFactory = mock(BitnetErrorFactory.class);
        authenticationManager = mock(FeignAuthenticationManager.class);
        target = new BitnetErrorDecoder(errorFactory, authenticationManager);
    }

    @Test
    public void shouldReturnBitnetInvalidRequestExceptionWhenResponseStatusIs400() {
        Response invalidRequestResponse = getResponse(400);
        BitnetInvalidRequestException invalidRequestException = mock(BitnetInvalidRequestException.class);
        when(errorFactory.getBitnetInvalidRequestException(invalidRequestResponse)).thenReturn(invalidRequestException);

        assertThat(invalidRequestException, is(equalTo((BitnetInvalidRequestException) target.decode("someKey", invalidRequestResponse))));
    }

    @Test
    public void shouldReturnBitnetExceptionWhenResponseStatusIsNotHandledWithASpecificError() {
        Response errorResponse = getResponse(408);
        BitnetException generalException = mock(BitnetException.class);
        when(errorFactory.getBitnetException(errorResponse)).thenReturn(generalException);

        assertThat(generalException, is(equalTo((BitnetException) target.decode("someKey", errorResponse))));
    }

    @Test
    public void shouldReturnBitnetResourceNotFoundExceptionWhenResponseStatusIs404() {
        Response errorResponse = getResponse(404);
        BitnetResourceNotFoundException resourceNotFoundException = mock(BitnetResourceNotFoundException.class);
        when(errorFactory.getBitnetResourceNotFoundException(errorResponse)).thenReturn(resourceNotFoundException);

        assertThat(resourceNotFoundException, is(equalTo((BitnetResourceNotFoundException) target.decode("someKey", errorResponse))));
    }

    @Test
    public void shouldReturnBitnetConflictExceptionWhenResponseStatusIs409() {
        Response errorResponse = getResponse(409);
        BitnetConflictException conflictException = mock(BitnetConflictException.class);
        when(errorFactory.getBitnetConflictException(errorResponse)).thenReturn(conflictException);

        assertThat(conflictException, is(equalTo((BitnetConflictException) target.decode("someKey", errorResponse))));
    }

    @Test
    public void shouldReturnBitnetRequestCouldNotBeProcessedExceptionWhenResponseStatusIs422() {
        Response errorResponse = getResponse(422);
        BitnetRequestCouldNotBeProcessedException requestCouldNotBeProcessedException = mock(BitnetRequestCouldNotBeProcessedException.class);
        when(errorFactory.getBitnetRequestCouldNotBeProcessedException(errorResponse)).thenReturn(requestCouldNotBeProcessedException);

        assertThat(requestCouldNotBeProcessedException, is(equalTo((BitnetRequestCouldNotBeProcessedException) target.decode("someKey", errorResponse))));
    }

    @Test
    public void shouldReturnBitnetAccessDeniedExceptionWhenResponseStatusIs401() {
        Response errorResponse = getResponse(401);
        BitnetAccessDeniedException accessDeniedException = mock(BitnetAccessDeniedException.class);
        when(errorFactory.getBitnetAccessDeniedException(errorResponse)).thenReturn(accessDeniedException);

        assertThat(accessDeniedException, is(equalTo((BitnetAccessDeniedException) target.decode("someKey", errorResponse))));
    }

    @Test
    public void shouldSetIsAuthenticationRequiredWhenAccessDeniedErrorOccurs() {
        Response errorResponse = getResponse(401);

        target.decode("someKey", errorResponse);

        verify(authenticationManager).resetAuthenticationStatus();
    }

    @Test
    public void shouldReturnBitnetRequestForbiddenExceptionWhenResponseStatusIs403() {
        Response errorResponse = getResponse(403);
        BitnetRequestForbiddenException requestForbiddenException = mock(BitnetRequestForbiddenException.class);
        when(errorFactory.getBitnetRequestForbiddenException(errorResponse)).thenReturn(requestForbiddenException);

        assertThat(requestForbiddenException, is(equalTo((BitnetRequestForbiddenException) target.decode("someKey", errorResponse))));
    }

    private Response getResponse(int httpStatusCode) {
        return Response.create(httpStatusCode, "A reason", new HashMap<String, Collection<String>>(), mock(Response.Body.class));
    }
}
