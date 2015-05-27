/*
 * Copyright 2015 Bitnet.
 */
package io.bitnet.feign.encoders;

import com.google.common.io.Files;
import feign.codec.Encoder;
import io.bitnet.model.payer.Payer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.impl.SimpleLogger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.mockito.Mockito.mock;


/**
 * The integration tests for {@link RequestLogger}.
 */
public class RequestLoggerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private final Encoder encoder = mock(Encoder.class);

    @Test
    public void shouldLogRequest() throws IOException, InterruptedException {
        File logFile = testFolder.newFile("application.log");
        System.setProperty(SimpleLogger.LOG_FILE_KEY, logFile.getAbsolutePath());

        new RequestLogger(encoder).encode(new Payer().withEmail("Bandit"), null, null);

        String logText = Files.toString(logFile, Charset.defaultCharset());
        assertThat(logText, stringContainsInOrder(Payer.class.getCanonicalName(), "email=Bandit"));
    }

}
