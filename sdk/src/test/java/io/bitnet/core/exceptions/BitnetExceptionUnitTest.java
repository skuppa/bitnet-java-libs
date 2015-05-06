/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.exceptions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * The unit tests for {@link io.bitnet.core.exceptions.BitnetException}.
 */
public class BitnetExceptionUnitTest {

    @Test
    public void shouldConstructException() {
        List<String> errorMessages = getErrorMessages();

        BitnetException exception = new BitnetException(errorMessages, "correlationId", "body");

        assertThat(errorMessages, is(equalTo(exception.getErrorMessages())));
        assertThat("correlationId", is(equalTo("correlationId")));
        assertThat("body", is(equalTo("body")));
    }

    @Test
    public void shouldConstructExceptionWithMessageWhenErrorMessagesAvailable() {
        List<String> errorMessages = getErrorMessages();

        BitnetException exception = new BitnetException(errorMessages, null, null);

        assertThat("an error occurred", is(equalTo(exception.getMessage())));
    }

    @Test
    public void shouldConstructExceptionWithGeneralMessageWhenNoErrorMessagesAvailable() {
        BitnetException exception = new BitnetException(new ArrayList<String>(), null, null);

        assertThat("An error occurred when using Bitnet services. Check exception properties for more details.", is(equalTo(exception.getMessage())));
    }

    @Test
    public void shouldConstructExceptionWithGeneralMessageWhenErrorMessagesAreNull() {
        BitnetException exception = new BitnetException(null, null, null);

        assertThat("An error occurred when using Bitnet services. Check exception properties for more details.", is(equalTo(exception.getMessage())));
    }

    private List<String> getErrorMessages() {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("an error occurred");
        return errorMessages;
    }
}
