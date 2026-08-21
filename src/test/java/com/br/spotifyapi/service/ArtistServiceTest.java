package com.br.spotifyapi.service;

import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.exceptions.ArtistNotFoundException;
import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Album;
import com.br.spotifyapi.models.entites.Artist;
import com.br.spotifyapi.models.mapper.AlbumMapper;
import com.br.spotifyapi.models.mapper.ArtistMapper;
import com.br.spotifyapi.repositories.AlbumRepository;
import com.br.spotifyapi.repositories.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private SpotifyClient spotifyClient;

    @Mock
    private SpotifyAuthService spotifyAuthService;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private ArtistMapper artistMapper;

    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private ArtistService artistService;

    private Artist artist;
    private ArtistResponse artistResponse;

    @BeforeEach
    void setUp() {

        artist = new Artist();
        artist.setId(1L);
        artist.setSpotifyId("spotify123");
        artist.setName("Artista Teste");
        artist.setSpotifyUrl("https://spotify.com/artista");


        artistResponse = new ArtistResponse(
                1L,
                "spotify123",
                "Artista Teste",
                "https://spotify.com/artista"

        );
    }

    // =========================
    // FIND BY ID
    // =========================

    @Test
    void shouldFindArtistById() {

        when(artistRepository.findById(1L))
                .thenReturn(Optional.of(artist));

        when(artistMapper.toArtistResponse(artist))
                .thenReturn(artistResponse);

        ArtistResponse result = artistService.findById(1L);

        assertEquals(artistResponse, result);

        verify(artistRepository).findById(1L);
        verify(artistMapper).toArtistResponse(artist);
    }

    @Test
    void shouldThrowExceptionWhenArtistNotFound() {

        when(artistRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ArtistNotFoundException.class,
                () -> artistService.findById(1L)
        );

        verify(artistRepository).findById(1L);
    }

    // =========================
    // FIND ALL
    // =========================

    @Test
    void shouldFindAllArtists() {

        List<Artist> artists = List.of(artist);
        List<ArtistResponse> responses = List.of(artistResponse);

        when(artistRepository.findAll())
                .thenReturn(artists);

        when(artistMapper.toArtistResponseList(artists))
                .thenReturn(responses);

        List<ArtistResponse> result = artistService.findAll();

        assertEquals(1, result.size());

        // PRIMEIRA ALTERAÇÃO É AQUI
        assertEquals(artistResponse, result.get(0));

        verify(artistRepository).findAll();
        verify(artistMapper).toArtistResponseList(artists);
    }

    // =========================
    // FIND ALBUMS BY ARTIST
    // =========================

    @Test
    void shouldFindAlbumsByArtist() {

        Album album = new Album();

        List<Album> albums = List.of(album);
        List<AlbumResponse> responses = List.of();

        when(artistRepository.findById(1L))
                .thenReturn(Optional.of(artist));

        when(albumRepository.findByArtistId(1L))
                .thenReturn(albums);

        when(albumMapper.toAlbumResponseList(albums))
                .thenReturn(responses);

        List<AlbumResponse> result =
                artistService.findAlbumsByArtist(1L);

        assertNotNull(result);

        verify(artistRepository).findById(1L);
        verify(albumRepository).findByArtistId(1L);
        verify(albumMapper).toAlbumResponseList(albums);
    }

    @Test
    void shouldThrowExceptionWhenFindingAlbumsOfNonexistentArtist() {

        when(artistRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ArtistNotFoundException.class,
                () -> artistService.findAlbumsByArtist(1L)
        );

        verify(artistRepository).findById(1L);

        verify(albumRepository, never())
                .findByArtistId(anyLong());
    }
}