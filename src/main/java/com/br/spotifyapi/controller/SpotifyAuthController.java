package com.br.spotifyapi.controller;

import com.br.spotifyapi.service.SpotifyAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    @GetMapping("/token")
    public String getToken() {
        return spotifyAuthService.getAccessToken();
    }
}
