/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

import feign.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit tests for {@link io.bitnet.feign.decoders.BitnetErrorResponseInterrogator}.
 */
public class BitnetErrorResponseInterrogatorUnitTest {

    private BitnetErrorResponseInterrogator target;

    private static final String jsonErrorBody = "{\n" +
            "  \"_links\" : {\n" +
            "    \"self\" : {\n" +
            "      \"href\" : \"/payer/v1/payers\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"correlationId\" : \"0a9e9d43-550a-4ce4-a7f2-0a25c57a63c0\",\n" +
            "  \"errors\" : [ {\n" +
            "    \"message\" : \"object has missing required properties\",\n" +
            "    \"missing\" : [ \"accountId\", \"email\" ],\n" +
            "    \"required\" : [ \"accountId\", \"email\" ]\n" +
            "  }, \n" +
            "  { \n" +
            "    \"message\" : \"Payer already exists with reference 'my-customer-001'\"\n" +
            "  } ]\n" +
            "}";

    private static final String malformedJsonBody = "{\n" +
            "  \"_links\" : {\n" +
            "    \"self\" : {\n" +
            "      \"href\" : \"/payer/v1/payers\"\n";

    private static final String jsonErrorBodyWithoutInfo = "{ }";

    @Before
    public void setUp() {
        target = new BitnetErrorResponseInterrogator();
    }

    @Test
    public void shouldGetErrorMessagesFromError() {
        List<String> errorMessages = target.getErrorMessages(jsonErrorBody);

        assertEquals("\"message\":\"object has missing required properties\",\"missing\":[\"accountId\",\"email\"],\"required\":[\"accountId\",\"email\"]", errorMessages.get(0));
        assertEquals("\"message\":\"Payer already exists with reference 'my-customer-001'\"", errorMessages.get(1));
    }

    @Test
    public void shouldGetNoMessagesWhenNoneAvailable() {
        List<String> errorMessages = target.getErrorMessages(jsonErrorBodyWithoutInfo);

        assertThat(0, is(errorMessages.size()));
    }

    @Test
    public void shouldGetNoResponseBodyWhenIOExceptionOccursReadingBody() throws IOException {
        String responseBody = target.getResponseBody(getResponseWhichThrowsIOError());

        assertThat(responseBody, is(equalTo("")));
    }

    @Test
    public void shouldGetNoErrorMessagesWhenResponseBodyEmpty() {
        List<String> errorMessages = target.getErrorMessages("");

        assertThat(0, is(errorMessages.size()));
    }

    @Test
    public void shouldGetCorrelationIdWhenAvailable() {
        String correlationId = target.getCorrelationId(jsonErrorBody);

        assertEquals("The correlationId was not retrieved correctly", "0a9e9d43-550a-4ce4-a7f2-0a25c57a63c0", correlationId);
    }

    @Test
    public void shouldGetNoCorrelationIdWhenNotAvailableAvailable() {
        String correlationId = target.getCorrelationId(jsonErrorBodyWithoutInfo);

        assertEquals("", correlationId);
    }

    @Test
    public void shouldGetResponseBodyWhenAvailable() throws IOException {
        String body = target.getBody(jsonErrorBody);

        assertThat(body, containsString("_link"));
    }

    @Test
    public void shouldGetBodyWhenCannotBeParsedDueToMalformedJson() {
        String body = target.getBody(malformedJsonBody);

        assertThat(body, containsString("_link"));
    }

    @Test
    public void shouldGetResponseBody() throws IOException {
        String responseBody = target.getResponseBody(getResponse(jsonErrorBody));

        assertThat(responseBody.length(), is(greaterThan(1)));
    }

    @Test
    public void shouldGetEmptyResponseBodyWhenBodyNull() throws IOException {
        Response.Body input = null;
        String responseBody = target.getResponseBody(Response.create(404, "A reason for failure", new HashMap<String, Collection<String>>(), input));

        assertThat(responseBody, is(equalTo("")));
    }

    private Response getResponse(String responseBody) throws IOException {
        Response.Body input = mock(Response.Body.class);
        when(input.asInputStream()).thenReturn(new ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        when(input.length()).thenReturn(responseBody.length());

        return Response.create(404, "A reason for failure", new HashMap<String, Collection<String>>(), input);
    }

    private Response getResponseWhichThrowsIOError() throws IOException {
        Response.Body input = mock(Response.Body.class);
        when(input.asInputStream()).thenThrow(new IOException());

        return Response.create(404, "A reason for failure", new HashMap<String, Collection<String>>(), input);
    }
}
