
package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.service.ArtistService;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistControllerTest {

    private final ArtistService artistService =
            mock(ArtistService.class);

    private final ArtistController artistController =
            new ArtistController(artistService);

    @Test
    void shouldFindArtistById() {

        ArtistResponse response =
                new ArtistResponse(
                        1L,
                        "spotify123",
                        "Artista Teste",
                        "https://spotify.com/artista",
                        90
                );

        when(artistService.findById(1L))
                .thenReturn(response);

        ResponseEntity<ArtistResponse> result =
                artistController.findById(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldFindAllArtists() {

        ArtistResponse artist =
                new ArtistResponse(
                        1L,
                        "spotify123",
                        "Artista Teste",
                        "https://spotify.com/artista",
                        90
                );

        when(artistService.findAll())
                .thenReturn(List.of(artist));

        ResponseEntity<List<ArtistResponse>> result =
                artistController.findAll();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().size());
    }
}