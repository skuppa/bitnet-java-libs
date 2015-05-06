/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

/**
 * This describes http status codes.
 */
class HttpStatusCode {

    public static final int INVALID_REQUEST = 400;

    public static final int ACCESS_DENIED = 401;

    public static final int REQUEST_FORBIDDEN = 403;

    public static final int RESOURCE_NOT_FOUND = 404;

    public static final int CONFLICT = 409;

    public static final int REQUEST_CANNOT_BE_PROCESSED = 422;
}
