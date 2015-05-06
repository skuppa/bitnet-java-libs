/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.decoders;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.bitnet.core.exceptions.BitnetAccessDeniedException;
import io.bitnet.feign.FeignAuthenticationManager;

import static io.bitnet.feign.decoders.HttpStatusCode.*;
;

/**
 * Decoder for interpreting bitnet error responses.
 */
public class BitnetErrorDecoder implements ErrorDecoder {

    private final BitnetErrorFactory errorFactory;
    private final FeignAuthenticationManager authenticationManager;

    public BitnetErrorDecoder(BitnetErrorFactory errorFactory, FeignAuthenticationManager authenticationManager) {
        this.errorFactory = errorFactory;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case INVALID_REQUEST:
                return errorFactory.getBitnetInvalidRequestException(response);
            case REQUEST_FORBIDDEN:
                return errorFactory.getBitnetRequestForbiddenException(response);
            case ACCESS_DENIED:
                return processAccessDeniedError(response);
            case RESOURCE_NOT_FOUND:
                return errorFactory.getBitnetResourceNotFoundException(response);
            case CONFLICT:
                return errorFactory.getBitnetConflictException(response);
            case REQUEST_CANNOT_BE_PROCESSED:
                return errorFactory.getBitnetRequestCouldNotBeProcessedException(response);
        }

        return errorFactory.getBitnetException(response);
    }

    private BitnetAccessDeniedException processAccessDeniedError(Response response) {
        authenticationManager.resetAuthenticationStatus();
        return errorFactory.getBitnetAccessDeniedException(response);
    }
}
    

