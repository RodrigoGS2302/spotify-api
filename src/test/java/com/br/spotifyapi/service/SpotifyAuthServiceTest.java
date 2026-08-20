
package com.br.spotifyapi.service;

import com.br.spotifyapi.client.SpotifyAuthClient;
import com.br.spotifyapi.client.dto.SpotifyTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpotifyAuthServiceTest {

    @Mock
    private SpotifyAuthClient spotifyAuthClient;

    private SpotifyAuthService spotifyAuthService;

    @BeforeEach
    void setUp() {

        spotifyAuthService =
                new SpotifyAuthService(spotifyAuthClient);

        ReflectionTestUtils.setField(
                spotifyAuthService,
                "clientId",
                "client-test"
        );

        ReflectionTestUtils.setField(
                spotifyAuthService,
                "clientSecret",
                "secret-test"
        );
    }

    @Test
    void shouldReturnAccessToken() {

        SpotifyTokenResponse tokenResponse =
                new SpotifyTokenResponse(
                        "token123",
                        "Bearer",
                        3600
                );

        when(spotifyAuthClient.getToken(anyString(), any()))
                .thenReturn(tokenResponse);

        String result =
                spotifyAuthService.getAccessToken();

        assertEquals("token123", result);
    }

    @Test
    void shouldReuseValidToken() {

        SpotifyTokenResponse tokenResponse =
                new SpotifyTokenResponse(
                        "token123",
                        "Bearer",
                        3600
                );

        when(spotifyAuthClient.getToken(anyString(), any()))
                .thenReturn(tokenResponse);

        spotifyAuthService.getAccessToken();
        spotifyAuthService.getAccessToken();

        verify(spotifyAuthClient, times(1))
                .getToken(anyString(), any());
    }
}