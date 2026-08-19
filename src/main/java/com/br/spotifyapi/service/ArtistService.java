package com.br.spotifyapi.service;


import com.br.spotifyapi.client.SpotifyAuthClient;
import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.models.dto.AlbumClientResponse;
import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.AlbumsClientResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Album;
import com.br.spotifyapi.models.entites.Artist;
import com.br.spotifyapi.models.mapper.AlbumMapper;
import com.br.spotifyapi.models.mapper.ArtistMapper;
import com.br.spotifyapi.repositories.AlbumRepository;
import com.br.spotifyapi.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final SpotifyClient spotifyClient;
    private final SpotifyAuthService spotifyAuthService;
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final AlbumRepository albumRepository;

    public ArtistResponse saveArtist (String spotifyId){

        validateArtistAlreadyExists(spotifyId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        ArtistClientResponse artistClientResponse = spotifyClient.getArtist(spotifyId, authorization);

        Artist artist = artistMapper.toArtist(artistClientResponse);

        Artist saveArtist = artistRepository.save(artist);

        return artistMapper.toArtistResponse(saveArtist);
    }

    public List<AlbumResponse> saveAlbums(String spotifyArtistId){

        Artist artist = validateArtistExists(spotifyArtistId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        AlbumsClientResponse albumsClientResponse = spotifyClient.getAlbums(spotifyArtistId, authorization);

        return saveAlbumList(albumsClientResponse.items(), artist);
    }

    private void validateArtistAlreadyExists(String spotifyId) {

        if (artistRepository.existsBySpotifyId(spotifyId)) {
            throw new IllegalArgumentException("Artista já cadastrado");
        }
    }

    private Artist validateArtistExists(String spotifyArtistId) {

        return artistRepository.findBySpotifyId(spotifyArtistId).orElseThrow(() ->
                        new IllegalArgumentException("Artista não encontrado"));
    }

    private List<AlbumResponse> saveAlbumList(List<AlbumClientResponse> albums, Artist artist) {

        List<AlbumResponse> albumsResponse = new ArrayList<>();

        for (AlbumClientResponse albumClientResponse : albums) {

            Album album = albumMapper.toAlbum(albumClientResponse, artist);

            Album savedAlbum = albumRepository.save(album);

            AlbumResponse albumResponse = albumMapper.toAlbumResponse(savedAlbum);

            albumsResponse.add(albumResponse);
        }

        return albumsResponse;
    }


}
