package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    private PlaylistController playlistController;

    private PlaylistResponse playlistResponse;

    @BeforeEach
    void setUp() {

        playlistController =
                new PlaylistController(playlistService);

        playlistResponse =
                new PlaylistResponse(
                        1L,
                        "Treino Pesado",
                        "Playlist para academia",
                        LocalDateTime.now(),
                        List.of()
                );
    }

    @Test
    void shouldCreatePlaylist() {

        PlaylistRequest request =
                new PlaylistRequest(
                        "Treino Pesado",
                        "Playlist para academia"
                );

        when(playlistService.createPlaylist(request))
                .thenReturn(playlistResponse);

        ResponseEntity<PlaylistResponse> response =
                playlistController.createPlaylist(request);

        assertEquals(201, response.getStatusCode().value());

        assertEquals(
                playlistResponse,
                response.getBody()
        );

        verify(playlistService)
                .createPlaylist(request);
    }

    @Test
    void shouldFindPlaylistByName() {

        when(playlistService
                .findPlaylistByName("Treino Pesado"))
                .thenReturn(playlistResponse);

        ResponseEntity<PlaylistResponse> response =
                playlistController.findPlaylistByName(
                        "Treino Pesado"
                );

        assertEquals(
                200,
                response.getStatusCode().value()
        );

        assertEquals(
                playlistResponse,
                response.getBody()
        );

        verify(playlistService)
                .findPlaylistByName("Treino Pesado");
    }

    @Test
    void shouldFindAllPlaylists() {

        List<PlaylistResponse> playlists =
                List.of(playlistResponse);

        when(playlistService.findAllPlaylist())
                .thenReturn(playlists);

        ResponseEntity<List<PlaylistResponse>> response =
                playlistController.findAllPlaylist();

        assertEquals(
                200,
                response.getStatusCode().value()
        );

        assertEquals(
                1,
                response.getBody().size()
        );

        assertEquals(
                playlistResponse,
                response.getBody().get(0)
        );

        verify(playlistService)
                .findAllPlaylist();
    }

    @Test
    void shouldAddTrack() {

        TrackResponse trackResponse =
                new TrackResponse(
                        1L,
                        "2nLtzopw4rPReszdYBJU6h",
                        "Numb"
                );

        when(playlistService.addTrack(
                1L,
                "2nLtzopw4rPReszdYBJU6h"))
                .thenReturn(trackResponse);

        ResponseEntity<TrackResponse> response =
                playlistController.addTrack(
                        1L,
                        "2nLtzopw4rPReszdYBJU6h"
                );

        assertEquals(
                201,
                response.getStatusCode().value()
        );

        assertEquals(
                trackResponse,
                response.getBody()
        );

        verify(playlistService)
                .addTrack(
                        1L,
                        "2nLtzopw4rPReszdYBJU6h"
                );
    }
}