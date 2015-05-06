/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.RetryableException;
import io.bitnet.core.exceptions.BitnetRetryableException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * The unit tests for {@link io.bitnet.feign.ConfigurableRetryer}.
 * These have been taken as a direct copy from the feign default implementation with an extra case for our new condition.
 */
public class ConfigurableRetryerUnitTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void only5TriesAllowedAndExponentialBackoff() throws Exception {
        RetryableException e = new RetryableException(null, null, null);
        ConfigurableRetryer retryer = new ConfigurableRetryer();
        assertEquals(1, retryer.attempt);
        assertEquals(0, retryer.sleptForMillis);

        retryer.continueOrPropagate(e);
        assertEquals(2, retryer.attempt);
        assertEquals(150, retryer.sleptForMillis);

        retryer.continueOrPropagate(e);
        assertEquals(3, retryer.attempt);
        assertEquals(375, retryer.sleptForMillis);

        retryer.continueOrPropagate(e);
        assertEquals(4, retryer.attempt);
        assertEquals(712, retryer.sleptForMillis);

        retryer.continueOrPropagate(e);
        assertEquals(5, retryer.attempt);
        assertEquals(1218, retryer.sleptForMillis);

        thrown.expect(RetryableException.class);
        retryer.continueOrPropagate(e);
    }

    @Test
    public void only1RetryAllowedForBitnetRetryableException() throws Exception {
        RetryableException e = mock(BitnetRetryableException.class);
        ConfigurableRetryer retryer = new ConfigurableRetryer();
        retryer.continueOrPropagate(e);
        try {
            retryer.continueOrPropagate(e);
            fail();
        } catch (BitnetRetryableException exception) {
            assertEquals(2, retryer.attempt);
        }
    }

    @Test
    public void considersRetryAfterButNotMoreThanMaxPeriod() throws Exception {
        ConfigurableRetryer retryer = new ConfigurableRetryer() {
            protected long currentTimeMillis() {
                return 0;
            }
        };

        retryer.continueOrPropagate(new RetryableException(null, null, new Date(5000)));
        assertEquals(2, retryer.attempt);
        assertEquals(1000, retryer.sleptForMillis);
    }
}
