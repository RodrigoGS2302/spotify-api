
package com.br.spotifyapi.mapper;

import com.br.spotifyapi.client.dto.ExternalUrlsResponse;
import com.br.spotifyapi.models.dto.AlbumClientResponse;
import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.entites.Album;
import com.br.spotifyapi.models.entites.Artist;
import com.br.spotifyapi.models.mapper.AlbumMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlbumMapperTest {

    private final AlbumMapper albumMapper = new AlbumMapper();

    @Test
    void shouldConvertClientResponseToAlbum() {

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("Artista Teste");

        ExternalUrlsResponse externalUrls =
                new ExternalUrlsResponse("https://spotify.com/album");

        AlbumClientResponse clientResponse =
                new AlbumClientResponse(
                        "album123",
                        "Álbum Teste",
                        "2026-01-01",
                        10,
                        externalUrls
                );

        Album album =
                albumMapper.toAlbum(clientResponse, artist);

        assertEquals("album123", album.getSpotifyId());
        assertEquals("Álbum Teste", album.getName());
        assertEquals("2026-01-01", album.getReleaseDate());
        assertEquals(10, album.getTotalTracks());
        assertEquals("https://spotify.com/album", album.getSpotifyUrl());
        assertEquals(artist, album.getArtist());
    }

    @Test
    void shouldConvertAlbumToResponse() {

        Album album = new Album();

        album.setId(1L);
        album.setSpotifyId("album123");
        album.setName("Álbum Teste");
        album.setReleaseDate("2026-01-01");
        album.setTotalTracks(10);
        album.setSpotifyUrl("https://spotify.com/album");

        AlbumResponse response =
                albumMapper.toAlbumResponse(album);

        assertEquals(1L, response.id());
        assertEquals("album123", response.spotifyId());
        assertEquals("Álbum Teste", response.name());
        assertEquals("2026-01-01", response.releaseDate());
        assertEquals(10, response.totalTracks());
        assertEquals(
                "https://spotify.com/album",
                response.spotifyUrl()
        );
    }
}