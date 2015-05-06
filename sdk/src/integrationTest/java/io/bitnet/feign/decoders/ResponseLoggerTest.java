/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.decoders;

import com.google.common.io.Files;
import feign.Response;
import feign.codec.Decoder;
import io.bitnet.model.payer.payer.Payer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.impl.SimpleLogger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * The integration tests for {@link ResponseLogger}.
 */
public class ResponseLoggerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private final Decoder decoder = mock(Decoder.class);

    @Test
    public void shouldLogResponse() throws IOException, InterruptedException {
        Map<String, Collection<String>> headers = Collections.singletonMap("Correlation-Id", (Collection<String>) Collections.singletonList("821584d8-37e8-4e79-b801-dc506409b0ec"));
        Response response = Response.create(200, "Success", headers, "{\"email\" : \"James\"}", Charset.defaultCharset());

        when(decoder.decode(any(Response.class), any(Type.class))).thenReturn(new Payer().withEmail("James"));

        File logFile = testFolder.newFile("application.log");
        System.setProperty(SimpleLogger.LOG_FILE_KEY, logFile.getAbsolutePath());

        new ResponseLogger(decoder).decode(response, null);

        String logText = Files.toString(logFile, Charset.defaultCharset());
        assertThat(logText, stringContainsInOrder(Payer.class.getCanonicalName(), "email=James"));
        assertThat(logText, stringContainsInOrder(Payer.class.getCanonicalName(), "821584d8-37e8-4e79-b801-dc506409b0ec"));
    }

}
