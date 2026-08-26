package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.service.ArtistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistControllerTest {

    @Mock
    private ArtistService artistService;

    private ArtistController artistController;

    private ArtistResponse artistResponse;

    @BeforeEach
    void setUp() {

        artistController = new ArtistController(artistService);

        artistResponse = new ArtistResponse(
                1L,
                "spotify123",
                "Artista Teste",
                "https://spotify.com/artista"
        );
    }

    @Test
    void shouldSaveArtist() {

        when(artistService.saveArtist("spotify123"))
                .thenReturn(artistResponse);

        ResponseEntity<ArtistResponse> result =
                artistController.saveArtist("spotify123");

        assertEquals(201, result.getStatusCode().value());
        assertEquals(artistResponse, result.getBody());

        verify(artistService).saveArtist("spotify123");
    }

    @Test
    void shouldFindArtistById() {

        when(artistService.findById(1L))
                .thenReturn(artistResponse);

        ResponseEntity<ArtistResponse> result =
                artistController.findById(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(artistResponse, result.getBody());

        verify(artistService).findById(1L);
    }

    @Test
    void shouldFindAllArtists() {

        Page<ArtistResponse> page =
                new PageImpl<>(List.of(artistResponse));

        when(artistService.findAll(0, 5, "asc"))
                .thenReturn(page);

        ResponseEntity<Page<ArtistResponse>> result =
                artistController.findAll(0, 5, "asc");

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getTotalElements());
        assertEquals(
                artistResponse,
                result.getBody().getContent().get(0)
        );

        verify(artistService).findAll(0, 5, "asc");
    }

    @Test
    void shouldFindAlbumsByArtist() {

        List<AlbumResponse> albums = List.of();

        when(artistService.findAlbumsByArtist(1L))
                .thenReturn(albums);

        ResponseEntity<List<AlbumResponse>> result =
                artistController.findAlbumsByArtist(1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(albums, result.getBody());

        verify(artistService).findAlbumsByArtist(1L);
    }

    @Test
    void shouldSaveAlbums() {

        List<AlbumResponse> albums = List.of();

        when(artistService.saveAlbums("spotify123"))
                .thenReturn(albums);

        ResponseEntity<List<AlbumResponse>> result =
                artistController.saveAlbums("spotify123");

        assertEquals(200, result.getStatusCode().value());
        assertEquals(albums, result.getBody());

        verify(artistService).saveAlbums("spotify123");
    }
}