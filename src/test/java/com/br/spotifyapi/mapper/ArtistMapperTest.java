
package com.br.spotifyapi.mapper;

import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.client.dto.ExternalUrlsResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Artist;
import com.br.spotifyapi.models.mapper.ArtistMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtistMapperTest {

    private final ArtistMapper artistMapper = new ArtistMapper();

    @Test
    void shouldConvertClientResponseToArtist() {

        ExternalUrlsResponse externalUrls =
                new ExternalUrlsResponse("https://spotify.com/artista");

        ArtistClientResponse clientResponse =
                new ArtistClientResponse(
                        "spotify123",
                        "Artista Teste",
                        90,
                        externalUrls
                );

        Artist artist = artistMapper.toArtist(clientResponse);

        assertEquals("spotify123", artist.getSpotifyId());
        assertEquals("Artista Teste", artist.getName());
        assertEquals(90, artist.getPopularity());
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
        artist.setSpotifyUrl("https://spotify.com/artista");
        artist.setPopularity(90);

        ArtistResponse response =
                artistMapper.toArtistResponse(artist);

        assertEquals(1L, response.id());
        assertEquals("spotify123", response.spotifyId());
        assertEquals("Artista Teste", response.name());
        assertEquals(
                "https://spotify.com/artista",
                response.spotifyUrl()
        );
        assertEquals(90, response.popularity());
    }
}