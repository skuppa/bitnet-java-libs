/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

import feign.Response;
import io.bitnet.core.exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This provides factory methods for Bitnet Errors.
 */
public class BitnetErrorFactory {

    private final BitnetErrorResponseInterrogator responseInterrogator;

    public BitnetErrorFactory(BitnetErrorResponseInterrogator responseInterrogator) {
        this.responseInterrogator = responseInterrogator;
    }

    public BitnetInvalidRequestException getBitnetInvalidRequestException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetInvalidRequestException(
                responseInterrogator.getErrorMessages(responseBody),
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }

    public BitnetException getBitnetException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetException(
                responseInterrogator.getErrorMessages(responseBody),
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }

    public BitnetResourceNotFoundException getBitnetResourceNotFoundException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetResourceNotFoundException(
                responseInterrogator.getErrorMessages(responseBody),
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }

    public BitnetConflictException getBitnetConflictException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetConflictException(
                responseInterrogator.getErrorMessages(responseBody),
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }

    public BitnetRequestCouldNotBeProcessedException getBitnetRequestCouldNotBeProcessedException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetRequestCouldNotBeProcessedException(
                responseInterrogator.getErrorMessages(responseBody),
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }

    public BitnetAccessDeniedException getBitnetAccessDeniedException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        return new BitnetAccessDeniedException(
                "Access denied to Bitnet Service. Check your credentials and permissions for the invoked service.",
                new Date(),
                responseInterrogator.getCorrelationId(responseBody));
    }

    public BitnetRequestForbiddenException getBitnetRequestForbiddenException(Response response) {
        String responseBody = responseInterrogator.getResponseBody(response);
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("Access to Bitnet Service is forbidden. Check your credentials and permissions for the invoked service.");
        return new BitnetRequestForbiddenException(
                errorMessages,
                responseInterrogator.getCorrelationId(responseBody),
                responseInterrogator.getBody(responseBody));
    }
}
