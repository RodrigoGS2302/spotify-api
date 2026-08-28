package com.br.spotifyapi.service;

import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.client.dto.TrackClientResponse;
import com.br.spotifyapi.exceptions.PlaylistNotFoundException;
import com.br.spotifyapi.exceptions.SpotifyApiException;
import com.br.spotifyapi.exceptions.TrackAlreadyExistsException;
import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.models.entites.Playlist;
import com.br.spotifyapi.models.entites.Track;
import com.br.spotifyapi.models.mapper.PlaylistMapper;
import com.br.spotifyapi.models.mapper.TrackMapper;
import com.br.spotifyapi.repositories.PlaylistRepository;
import com.br.spotifyapi.repositories.TrackRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock
    private TrackMapper trackMapper;

    @Mock
    private SpotifyClient spotifyClient;

    @Mock
    private SpotifyAuthService spotifyAuthService;

    @InjectMocks
    private PlaylistService playlistService;

    private Playlist playlist;
    private PlaylistRequest playlistRequest;
    private PlaylistResponse playlistResponse;

    @BeforeEach
    void setUp() {

        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Treino Pesado");
        playlist.setDescription("Playlist para academia");
        playlist.setCreatedAt(LocalDateTime.now());

        playlistRequest = new PlaylistRequest(
                "Treino Pesado",
                "Playlist para academia"
        );

        playlistResponse = new PlaylistResponse(
                1L,
                "Treino Pesado",
                "Playlist para academia",
                playlist.getCreatedAt(),
                List.of()
        );
    }

    @Test
    void shouldCreatePlaylist() {

        when(playlistRepository.existsByName("Treino Pesado"))
                .thenReturn(false);

        when(playlistMapper.toPlaylist(playlistRequest))
                .thenReturn(playlist);

        when(playlistRepository.save(playlist))
                .thenReturn(playlist);

        when(playlistMapper.toPlaylistResponse(playlist))
                .thenReturn(playlistResponse);

        PlaylistResponse result =
                playlistService.createPlaylist(playlistRequest);

        assertNotNull(result);
        assertEquals(playlistResponse, result);

        verify(playlistRepository)
                .existsByName("Treino Pesado");

        verify(playlistMapper)
                .toPlaylist(playlistRequest);

        verify(playlistRepository)
                .save(playlist);

        verify(playlistMapper)
                .toPlaylistResponse(playlist);
    }

    @Test
    void shouldThrowExceptionWhenPlaylistNameIsInvalid() {

        PlaylistRequest request = new PlaylistRequest(
                "Treino@Pesado",
                "Playlist para academia"
        );

        assertThrows(
                InvalidPlaylistNameException.class,
                () -> playlistService.createPlaylist(request)
        );

        verify(playlistRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsInvalid() {

        String description =
                "Esta descrição possui mais de cento e vinte caracteres " +
                        "e foi criada propositalmente apenas para validar a regra " +
                        "de tamanho máximo definida para uma playlist dentro da aplicação.";

        PlaylistRequest request = new PlaylistRequest(
                "Treino",
                description
        );

        when(playlistRepository.existsByName("Treino"))
                .thenReturn(false);

        assertThrows(
                InvalidDescriptionException.class,
                () -> playlistService.createPlaylist(request)
        );

        verify(playlistRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenPlaylistAlreadyExists() {

        when(playlistRepository.existsByName("Treino Pesado"))
                .thenReturn(true);

        assertThrows(
                PlaylistAlreadyExistsException.class,
                () -> playlistService.createPlaylist(playlistRequest)
        );

        verify(playlistRepository)
                .existsByName("Treino Pesado");

        verify(playlistRepository, never())
                .save(any());
    }

    @Test
    void shouldFindPlaylistByName() {

        when(playlistRepository
                .findPlaylistByNameIgnoreCase("treino pesado"))
                .thenReturn(Optional.of(playlist));

        when(playlistMapper.toPlaylistResponse(playlist))
                .thenReturn(playlistResponse);

        PlaylistResponse result =
                playlistService.findPlaylistByName("treino pesado");

        assertEquals(playlistResponse, result);

        verify(playlistRepository)
                .findPlaylistByNameIgnoreCase("treino pesado");

        verify(playlistMapper)
                .toPlaylistResponse(playlist);
    }

    @Test
    void shouldThrowExceptionWhenPlaylistNotFoundByName() {

        when(playlistRepository
                .findPlaylistByNameIgnoreCase("Inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(
                PlaylistNotFoundException.class,
                () -> playlistService.findPlaylistByName("Inexistente")
        );

        verify(playlistRepository)
                .findPlaylistByNameIgnoreCase("Inexistente");
    }

    @Test
    void shouldFindAllPlaylists() {

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setName("Rock Nacional");
        playlist2.setDescription("Rock brasileiro");
        playlist2.setCreatedAt(LocalDateTime.now());

        List<Playlist> playlists =
                List.of(playlist, playlist2);

        PlaylistResponse response2 =
                new PlaylistResponse(
                        2L,
                        "Rock Nacional",
                        "Rock brasileiro",
                        playlist2.getCreatedAt(),
                        List.of()
                );

        List<PlaylistResponse> responses =
                List.of(playlistResponse, response2);

        when(playlistRepository
                .findAllByOrderByCreatedAtAsc())
                .thenReturn(playlists);

        when(playlistMapper
                .toPlaylistResponseList(playlists))
                .thenReturn(responses);

        List<PlaylistResponse> result =
                playlistService.findAllPlaylist();

        assertEquals(2, result.size());
        assertEquals(responses, result);

        verify(playlistRepository)
                .findAllByOrderByCreatedAtAsc();

        verify(playlistMapper)
                .toPlaylistResponseList(playlists);
    }

    @Test
    void shouldAddTrack() {

        String spotifyTrackId =
                "2nLtzopw4rPReszdYBJU6h";

        String accessToken = "token-teste";
        String authorization = "Bearer token-teste";

        TrackClientResponse trackClientResponse =
                new TrackClientResponse(
                        spotifyTrackId,
                        "Numb"
                );

        Track track = new Track();
        track.setId(1L);
        track.setSpotifyId(spotifyTrackId);
        track.setName("Numb");
        track.setPlaylist(playlist);

        TrackResponse trackResponse =
                new TrackResponse(
                        1L,
                        spotifyTrackId,
                        "Numb"
                );

        when(playlistRepository.findById(1L))
                .thenReturn(Optional.of(playlist));

        when(spotifyAuthService.getAccessToken())
                .thenReturn(accessToken);

        when(spotifyClient.getTrack(
                spotifyTrackId,
                authorization))
                .thenReturn(trackClientResponse);

        when(trackRepository
                .existsBySpotifyIdAndPlaylistId(
                        spotifyTrackId,
                        1L))
                .thenReturn(false);

        when(trackMapper.toTrack(
                trackClientResponse,
                playlist))
                .thenReturn(track);

        when(trackRepository.save(track))
                .thenReturn(track);

        when(trackMapper.toTrackResponse(track))
                .thenReturn(trackResponse);

        TrackResponse result =
                playlistService.addTrack(
                        1L,
                        spotifyTrackId
                );

        assertEquals(trackResponse, result);

        verify(playlistRepository)
                .findById(1L);

        verify(spotifyAuthService)
                .getAccessToken();

        verify(spotifyClient)
                .getTrack(
                        spotifyTrackId,
                        authorization
                );

        verify(trackRepository)
                .existsBySpotifyIdAndPlaylistId(
                        spotifyTrackId,
                        1L
                );

        verify(trackRepository)
                .save(track);
    }

    @Test
    void shouldThrowExceptionWhenAddingTrackToNonexistentPlaylist() {

        when(playlistRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                PlaylistNotFoundException.class,
                () -> playlistService.addTrack(
                        999L,
                        "spotify123"
                )
        );

        verify(spotifyClient, never())
                .getTrack(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTrackAlreadyExists() {

        String spotifyTrackId = "spotify123";

        TrackClientResponse trackClientResponse =
                new TrackClientResponse(
                        spotifyTrackId,
                        "Numb"
                );

        when(playlistRepository.findById(1L))
                .thenReturn(Optional.of(playlist));

        when(spotifyAuthService.getAccessToken())
                .thenReturn("token");

        when(spotifyClient.getTrack(
                spotifyTrackId,
                "Bearer token"))
                .thenReturn(trackClientResponse);

        when(trackRepository
                .existsBySpotifyIdAndPlaylistId(
                        spotifyTrackId,
                        1L))
                .thenReturn(true);

        assertThrows(
                TrackAlreadyExistsException.class,
                () -> playlistService.addTrack(
                        1L,
                        spotifyTrackId
                )
        );

        verify(trackRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenSpotifyFails() {

        String spotifyTrackId = "spotify123";

        when(playlistRepository.findById(1L))
                .thenReturn(Optional.of(playlist));

        when(spotifyAuthService.getAccessToken())
                .thenReturn("token");

        when(spotifyClient.getTrack(
                spotifyTrackId,
                "Bearer token"))
                .thenThrow(mock(FeignException.class));

        assertThrows(
                SpotifyApiException.class,
                () -> playlistService.addTrack(
                        1L,
                        spotifyTrackId
                )
        );

        verify(trackRepository, never())
                .save(any());
    }
}