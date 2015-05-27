/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.core.cache;

import io.bitnet.model.token.Token;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit tests for {@link io.bitnet.core.cache.DefaultCache}.
 */
public class DefaultCacheUnitTest {

    private DefaultCache target;
    private Token accessToken;

    @Before
    public void setUp() {
        accessToken = mock(Token.class);
        target = new DefaultCache();
    }

    @Test
    public void shouldStoreAccessToken() {
        when(accessToken.getExpiresIn()).thenReturn(1000.0);

        target.storeAccessToken(accessToken);

        assertThat(target.getAccessToken(), is(equalTo(accessToken)));
    }

    @Test
    public void shouldNotStoreAccessTokenAfterExpiryTime() {
        when(accessToken.getExpiresIn()).thenReturn(0.0);

        target.storeAccessToken(accessToken);

        assertThat(target.getAccessToken(), is(equalTo(null)));
    }

    @Test
    public void shouldRemoveAccessToken() {
        when(accessToken.getExpiresIn()).thenReturn(10000.0);

        target.storeAccessToken(accessToken);
        target.removeAccessToken();

        assertThat(target.getAccessToken(), is(equalTo(null)));
    }

    @Test
    public void shouldRemoveAccessTokenWhenNoTokenStored() {
        target.removeAccessToken();
    }

    @Test
    public void shouldGetNullWhenNoAccessTokenStored() {
        assertThat(target.getAccessToken(), is(equalTo(null)));
    }

}
