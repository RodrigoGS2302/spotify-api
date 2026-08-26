package com.br.spotifyapi.service;

import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.client.dto.ExternalUrlsResponse;
import com.br.spotifyapi.exceptions.ArtistAlreadyExistsException;
import com.br.spotifyapi.exceptions.ArtistNotFoundException;
import com.br.spotifyapi.exceptions.InvalidSortDirectionException;
import com.br.spotifyapi.models.dto.ArtistResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        artist.setSpotifyUrl(
                "https://spotify.com/artista"
        );

        artistResponse =
                new ArtistResponse(
                        1L,
                        "spotify123",
                        "Artista Teste",
                        "https://spotify.com/artista"
                );
    }

    @Test
    void shouldFindArtistById() {

        when(artistRepository.findById(1L))
                .thenReturn(Optional.of(artist));

        when(artistMapper.toArtistResponse(artist))
                .thenReturn(artistResponse);

        ArtistResponse result =
                artistService.findById(1L);

        assertNotNull(result);

        assertEquals(
                artistResponse,
                result
        );

        verify(artistRepository)
                .findById(1L);

        verify(artistMapper)
                .toArtistResponse(artist);
    }

    @Test
    void shouldThrowExceptionWhenArtistNotFound() {

        when(artistRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ArtistNotFoundException.class,
                () -> artistService.findById(999L)
        );

        verify(artistRepository)
                .findById(999L);
    }

    @Test
    void shouldFindAllArtistsAscending() {

        Page<Artist> artistPage =
                new PageImpl<>(
                        List.of(artist)
                );

        when(artistRepository.findAll(
                any(Pageable.class)))
                .thenReturn(artistPage);

        when(artistMapper.toArtistResponse(artist))
                .thenReturn(artistResponse);

        Page<ArtistResponse> result =
                artistService.findAll(
                        0,
                        5,
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                1,
                result.getTotalElements()
        );

        assertEquals(
                "Artista Teste",
                result.getContent().get(0).name()
        );

        verify(artistRepository)
                .findAll(any(Pageable.class));
    }

    @Test
    void shouldFindAllArtistsDescending() {

        Page<Artist> artistPage =
                new PageImpl<>(
                        List.of(artist)
                );

        when(artistRepository.findAll(
                any(Pageable.class)))
                .thenReturn(artistPage);

        when(artistMapper.toArtistResponse(artist))
                .thenReturn(artistResponse);

        Page<ArtistResponse> result =
                artistService.findAll(
                        0,
                        5,
                        "desc"
                );

        assertNotNull(result);

        assertEquals(
                1,
                result.getTotalElements()
        );

        verify(artistRepository)
                .findAll(any(Pageable.class));
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {

        assertThrows(
                InvalidSortDirectionException.class,
                () -> artistService.findAll(
                        0,
                        5,
                        "banana"
                )
        );

        verify(
                artistRepository,
                never()
        ).findAll(any(Pageable.class));
    }

    @Test
    void shouldSaveArtist() {

        String spotifyId = "spotify123";

        String accessToken = "token-teste";

        String authorization =
                "Bearer token-teste";

        ExternalUrlsResponse externalUrls =
                new ExternalUrlsResponse(
                        "https://spotify.com/artista"
                );

        ArtistClientResponse clientResponse =
                new ArtistClientResponse(
                        spotifyId,
                        "Artista Teste",
                        externalUrls
                );

        when(artistRepository
                .existsBySpotifyId(spotifyId))
                .thenReturn(false);

        when(spotifyAuthService.getAccessToken())
                .thenReturn(accessToken);

        when(spotifyClient.getArtist(
                spotifyId,
                authorization))
                .thenReturn(clientResponse);

        when(artistMapper.toArtist(clientResponse))
                .thenReturn(artist);

        when(artistRepository.save(artist))
                .thenReturn(artist);

        when(artistMapper.toArtistResponse(artist))
                .thenReturn(artistResponse);

        ArtistResponse result =
                artistService.saveArtist(spotifyId);

        assertEquals(
                artistResponse,
                result
        );

        verify(artistRepository)
                .save(artist);
    }

    @Test
    void shouldThrowExceptionWhenArtistAlreadyExists() {

        when(artistRepository
                .existsBySpotifyId("spotify123"))
                .thenReturn(true);

        assertThrows(
                ArtistAlreadyExistsException.class,
                () -> artistService.saveArtist(
                        "spotify123"
                )
        );

        verify(
                spotifyClient,
                never()
        ).getArtist(
                anyString(),
                anyString()
        );

        verify(
                artistRepository,
                never()
        ).save(any());
    }
}