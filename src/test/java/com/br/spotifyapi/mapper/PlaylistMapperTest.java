package com.br.spotifyapi.mapper;

import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.models.entites.Playlist;
import com.br.spotifyapi.models.entites.Track;
import com.br.spotifyapi.models.mapper.PlaylistMapper;
import com.br.spotifyapi.models.mapper.TrackMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistMapperTest {

    @Mock
    private TrackMapper trackMapper;

    private PlaylistMapper playlistMapper;

    @BeforeEach
    void setUp() {
        playlistMapper = new PlaylistMapper(trackMapper);
    }

    @Test
    void shouldConvertPlaylistRequestToPlaylist() {

        PlaylistRequest request =
                new PlaylistRequest(
                        "Treino Pesado",
                        "Playlist para academia"
                );

        Playlist playlist =
                playlistMapper.toPlaylist(request);

        assertNotNull(playlist);

        assertEquals(
                "Treino Pesado",
                playlist.getName()
        );

        assertEquals(
                "Playlist para academia",
                playlist.getDescription()
        );

        assertNotNull(playlist.getCreatedAt());
    }

    @Test
    void shouldConvertPlaylistToPlaylistResponse() {

        Playlist playlist = new Playlist();

        playlist.setId(1L);
        playlist.setName("Treino Pesado");
        playlist.setDescription("Playlist para academia");

        LocalDateTime createdAt =
                LocalDateTime.now();

        playlist.setCreatedAt(createdAt);

        Track track = new Track();

        playlist.setTracks(List.of(track));

        TrackResponse trackResponse =
                new TrackResponse(
                        1L,
                        "spotify123",
                        "Numb"
                );

        when(trackMapper.toTrackResponseList(
                playlist.getTracks()))
                .thenReturn(List.of(trackResponse));

        PlaylistResponse response =
                playlistMapper.toPlaylistResponse(playlist);

        assertEquals(1L, response.id());
        assertEquals("Treino Pesado", response.name());
        assertEquals(
                "Playlist para academia",
                response.description()
        );
        assertEquals(createdAt, response.createdAt());

        assertEquals(1, response.tracks().size());
        assertEquals(trackResponse, response.tracks().get(0));
    }

    @Test
    void shouldConvertPlaylistListToPlaylistResponseList() {

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Treino");
        playlist.setDescription("Academia");
        playlist.setCreatedAt(LocalDateTime.now());

        when(trackMapper.toTrackResponseList(
                playlist.getTracks()))
                .thenReturn(List.of());

        List<PlaylistResponse> responses =
                playlistMapper.toPlaylistResponseList(
                        List.of(playlist)
                );

        assertEquals(1, responses.size());
        assertEquals("Treino", responses.get(0).name());
    }
}