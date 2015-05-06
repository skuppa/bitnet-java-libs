/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

import feign.Response;
import io.bitnet.core.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit tests for {@link io.bitnet.feign.decoders.BitnetErrorFactory}.
 */
public class BitnetErrorFactoryUnitTest {

    private BitnetErrorFactory target;
    private BitnetErrorResponseInterrogator responseInterrogator;
    private ArrayList<String> errorMessages;
    private Response errorResponse;

    @Before
    public void setUp() {
        responseInterrogator = mock(BitnetErrorResponseInterrogator.class);
        target = new BitnetErrorFactory(responseInterrogator);
        errorMessages = new ArrayList<>();
    }

    @Test
    public void shouldGetBitnetInvalidRequestException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetInvalidRequestException exception = target.getBitnetInvalidRequestException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    @Test
    public void shouldGetBitnetException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetException exception = target.getBitnetException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    @Test
    public void shouldGetBitnetResourceNotFoundException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetResourceNotFoundException exception = target.getBitnetResourceNotFoundException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    @Test
    public void shouldGetBitnetConflictException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetConflictException exception = target.getBitnetConflictException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    @Test
    public void shouldGetBitnetRequestCouldNotBeProcessedException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetRequestCouldNotBeProcessedException exception = target.getBitnetRequestCouldNotBeProcessedException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    @Test
    public void shouldGetBitnetAccessDeniedException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetAccessDeniedException accessDeniedException = target.getBitnetAccessDeniedException(errorResponse);

        assertThat("Access denied to Bitnet Service. Check your credentials and permissions for the invoked service.", is(equalTo(accessDeniedException.getMessage())));
        assertThat("12345", is(equalTo(accessDeniedException.getCorrelationId())));
        assertThat(currentDateTime(), greaterThanOrEqualTo(accessDeniedException.retryAfter()));
    }

    @Test
    public void shouldGetBitnetRequestForbiddenException() {
        setResponseInterrogatorToReturnBaseErrorInfo();

        BitnetRequestForbiddenException exception = target.getBitnetRequestForbiddenException(errorResponse);

        assertThatBaseErrorInfoIsContainedWithinException(exception);
    }

    private Date currentDateTime() {
        return new Date();
    }


    private void setResponseInterrogatorToReturnBaseErrorInfo() {
        errorResponse = getResponse(400);

        String responseBody = "sample body";
        when(responseInterrogator.getResponseBody(errorResponse)).thenReturn(responseBody);

        when(responseInterrogator.getBody(responseBody)).thenReturn("the body");
        when(responseInterrogator.getCorrelationId(responseBody)).thenReturn("12345");
        when(responseInterrogator.getErrorMessages(responseBody)).thenReturn(errorMessages);
    }

    private Response getResponse(int httpStatusCode) {
        return Response.create(httpStatusCode, "A reason", new HashMap<String, Collection<String>>(), mock(Response.Body.class));
    }

    private void assertThatBaseErrorInfoIsContainedWithinException(BitnetException exception) {
        assertThat("the body", is(equalTo(exception.getResponseBody())));
        assertThat("12345", is(equalTo(exception.getCorrelationId())));
        assertThat(errorMessages, is(equalTo(exception.getErrorMessages())));
    }
}
