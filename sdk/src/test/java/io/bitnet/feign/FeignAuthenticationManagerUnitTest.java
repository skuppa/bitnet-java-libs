/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign;

import io.bitnet.api.OAuthService;
import io.bitnet.core.cache.DefaultCache;
import io.bitnet.model.token.Token;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * The unit tests for {@link io.bitnet.feign.FeignAuthenticationManager}.
 */
public class FeignAuthenticationManagerUnitTest {

    private FeignAuthenticationManager target;
    private DefaultCache cache;
    private OAuthService oAuthService;
    private Token accessToken;

    @Before
    public void setUp() {
        FeignServiceProvider serviceProvider = mock(FeignServiceProvider.class);
        oAuthService = mock(OAuthService.class);
        when(serviceProvider.oAuthService()).thenReturn(oAuthService);
        accessToken = mock(Token.class);
        cache = mock(DefaultCache.class);
        target = new FeignAuthenticationManager(serviceProvider, cache);
    }

    @Test
    public void shouldAuthenticateWhenNotAuthenticated() {
        setOAuthServiceToReturnAuthenticationToken();

        assertThat("myBitnetToken", is(equalTo(target.authenticate())));
    }

    @Test
    public void shouldStoreAuthenticationTokenInCache() {
        setOAuthServiceToReturnAuthenticationToken();

        target.authenticate();

        verify(cache).storeAccessToken(accessToken);
    }

    @Test
    public void shouldGetTokenFromCacheWhenAvailable() {
        when(accessToken.getAccessToken()).thenReturn("myBitnetToken");
        when(cache.getAccessToken()).thenReturn(accessToken);

        assertThat("myBitnetToken", is(equalTo(target.authenticate())));
    }

    @Test
    public void shouldResetAuthentication() {
        target.resetAuthenticationStatus();

        verify(cache).removeAccessToken();
    }

    private void setOAuthServiceToReturnAuthenticationToken() {
        when(oAuthService.authenticate("client_credentials")).thenReturn(accessToken);
        when(accessToken.getAccessToken()).thenReturn("myBitnetToken");
    }
}
