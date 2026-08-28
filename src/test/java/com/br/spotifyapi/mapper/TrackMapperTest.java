package com.br.spotifyapi.mapper;

import com.br.spotifyapi.client.dto.TrackClientResponse;
import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.models.entites.Playlist;
import com.br.spotifyapi.models.entites.Track;
import com.br.spotifyapi.models.mapper.TrackMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackMapperTest {

    private TrackMapper trackMapper;

    @BeforeEach
    void setUp() {
        trackMapper = new TrackMapper();
    }

    @Test
    void shouldConvertTrackClientResponseToTrack() {

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Treino");

        TrackClientResponse response =
                new TrackClientResponse(
                        "spotify123",
                        "Numb"
                );

        Track track =
                trackMapper.toTrack(
                        response,
                        playlist
                );

        assertNotNull(track);

        assertEquals(
                "spotify123",
                track.getSpotifyId()
        );

        assertEquals(
                "Numb",
                track.getName()
        );

        assertEquals(
                playlist,
                track.getPlaylist()
        );
    }

    @Test
    void shouldConvertTrackToTrackResponse() {

        Track track = new Track();

        track.setId(1L);
        track.setSpotifyId("spotify123");
        track.setName("Numb");

        TrackResponse response =
                trackMapper.toTrackResponse(track);

        assertEquals(1L, response.id());

        assertEquals(
                "spotify123",
                response.spotifyId()
        );

        assertEquals(
                "Numb",
                response.name()
        );
    }

    @Test
    void shouldConvertTrackListToTrackResponseList() {

        Track track1 = new Track();

        track1.setId(1L);
        track1.setSpotifyId("spotify1");
        track1.setName("Numb");

        Track track2 = new Track();

        track2.setId(2L);
        track2.setSpotifyId("spotify2");
        track2.setName("The Scientist");

        List<TrackResponse> responses =
                trackMapper.toTrackResponseList(
                        List.of(track1, track2)
                );

        assertEquals(2, responses.size());

        assertEquals(
                "Numb",
                responses.get(0).name()
        );

        assertEquals(
                "The Scientist",
                responses.get(1).name()
        );
    }
}