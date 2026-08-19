package com.br.spotifyapi.service;

import com.br.spotifyapi.client.dto.SpotifyTokenResponse;
import com.br.spotifyapi.models.dto.AlbumResponse;
import org.springframework.beans.factory.annotation.Value;
import com.br.spotifyapi.client.SpotifyAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SpotifyAuthService {

    private final SpotifyAuthClient spotifyAuthClient;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    public String getAccessToken() {

        String credentials = clientId + ":" + clientSecret;

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        String authorization = "Basic " + encodedCredentials;

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        SpotifyTokenResponse spotifyTokenResponse = spotifyAuthClient.getToken(authorization, body);

        return spotifyTokenResponse.accessToken();
    }

}
