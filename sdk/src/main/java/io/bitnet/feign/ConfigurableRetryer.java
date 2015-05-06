/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import feign.RetryableException;
import feign.Retryer;
import io.bitnet.core.exceptions.BitnetRetryableException;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * This is a direct copy of the feign default implementation with a slight change so that
 * Bitnet Retryable Exceptions will only be retried the specified number of times.  This is to allow
 * for the fact that we typically don't want to retry access denied multiple times, etc.
 */
public class ConfigurableRetryer implements Retryer {
    private final int maxAttemptsForBitnetErrors;
    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt;
    long sleptForMillis;

    public ConfigurableRetryer() {
        this(100, SECONDS.toMillis(1), 5, 2);
    }

    private ConfigurableRetryer(long period, long maxPeriod, int maxAttempts, int maxAttemptsForBitnetErrors) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
        this.maxAttemptsForBitnetErrors = maxAttemptsForBitnetErrors;
    }

    // visible for testing;
    long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public void continueOrPropagate(RetryableException e) {
        checkIfMaxAttemptsExceeded(e);
        attempt++;

        long interval;
        if (e.retryAfter() != null) {
            interval = e.retryAfter().getTime() - currentTimeMillis();
            if (interval > maxPeriod) {
                interval = maxPeriod;
            }
            if (interval < 0) {
                return;
            }
        } else {
            interval = nextMaxInterval();
        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        sleptForMillis += interval;
    }

    private void checkIfMaxAttemptsExceeded(RetryableException exception) {
        if (exception instanceof BitnetRetryableException) {
            whenMaxAttemptsExceededThrowException(exception, maxAttemptsForBitnetErrors);
        } else {
            whenMaxAttemptsExceededThrowException(exception, maxAttempts);
        }
    }

    private void whenMaxAttemptsExceededThrowException(RetryableException exception, int maxAttempts) {
        if (attempt >= maxAttempts) {
            throw exception;
        }
    }

    /**
     * Calculates the time interval to a retry attempt. <br> The interval increases exponentially
     * with each attempt, at a rate of nextInterval *= 1.5 (where 1.5 is the backoff factor), to the
     * maximum interval.
     *
     * @return time in nanoseconds from now until the next attempt.
     */
    private long nextMaxInterval() {
        long interval = (long) (period * Math.pow(1.5, attempt - 1));
        return interval > maxPeriod ? maxPeriod : interval;
    }
}
