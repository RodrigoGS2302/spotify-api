package com.br.spotifyapi.mapper;

import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.client.dto.ExternalUrlsResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Artist;
import com.br.spotifyapi.models.mapper.ArtistMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistMapperTest {

    private final ArtistMapper artistMapper = new ArtistMapper();

    @Test
    void shouldConvertClientResponseToArtist() {

        ExternalUrlsResponse externalUrls =
                new ExternalUrlsResponse(
                        "https://spotify.com/artista"
                );

        ArtistClientResponse clientResponse =
                new ArtistClientResponse(
                        "spotify123",
                        "Artista Teste",
                        externalUrls
                );

        Artist artist = artistMapper.toArtist(clientResponse);

        assertEquals(
                "spotify123",
                artist.getSpotifyId()
        );

        assertEquals(
                "Artista Teste",
                artist.getName()
        );

        assertEquals(
                "https://spotify.com/artista",
                artist.getSpotifyUrl()
        );
    }

    @Test
    void shouldConvertArtistToResponse() {

        Artist artist = new Artist();

        artist.setId(1L);
        artist.setSpotifyId("spotify123");
        artist.setName("Artista Teste");
        artist.setSpotifyUrl(
                "https://spotify.com/artista"
        );

        ArtistResponse response =
                artistMapper.toArtistResponse(artist);

        assertEquals(
                1L,
                response.id()
        );

        assertEquals(
                "spotify123",
                response.spotifyId()
        );

        assertEquals(
                "Artista Teste",
                response.name()
        );

        assertEquals(
                "https://spotify.com/artista",
                response.spotifyUrl()
        );
    }

    @Test
    void shouldConvertArtistListToResponseList() {

        Artist artist = new Artist();

        artist.setId(1L);
        artist.setSpotifyId("spotify123");
        artist.setName("Artista Teste");
        artist.setSpotifyUrl(
                "https://spotify.com/artista"
        );

        List<ArtistResponse> responses =
                artistMapper.toArtistResponseList(
                        List.of(artist)
                );

        assertEquals(1, responses.size());

        assertEquals(
                "Artista Teste",
                responses.get(0).name()
        );
    }
}