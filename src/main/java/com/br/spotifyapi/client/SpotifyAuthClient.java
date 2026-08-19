package com.br.spotifyapi.client;

import com.br.spotifyapi.client.dto.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "spotifyAuthClient",
        url = "https://accounts.spotify.com"
)
public interface SpotifyAuthClient {

    @PostMapping(
            value = "/api/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    SpotifyTokenResponse getToken(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MultiValueMap<String, String> body
    );

}