package com.br.spotifyapi.service;


import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.exceptions.ArtistAlreadyExistsException;
import com.br.spotifyapi.exceptions.ArtistNotFoundException;
import com.br.spotifyapi.exceptions.SpotifyApiException;
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
import feign.FeignException;
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

    public ArtistResponse saveArtist(String spotifyId) {

        validateArtistAlreadyExists(spotifyId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        ArtistClientResponse artistClientResponse;

        try {
            artistClientResponse = spotifyClient.getArtist(spotifyId, authorization);
        } catch (FeignException e) {
            throw new SpotifyApiException("Erro ao consultar artista no Spotify");
        }

        Artist artist = artistMapper.toArtist(artistClientResponse);

        Artist savedArtist = artistRepository.save(artist);

        return artistMapper.toArtistResponse(savedArtist);
    }

    public List<AlbumResponse> saveAlbums(String spotifyArtistId){

        Artist artist = validateArtistExists(spotifyArtistId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        AlbumsClientResponse albumsClientResponse;

        try {
            albumsClientResponse = spotifyClient.getAlbums(spotifyArtistId, authorization);

        } catch (FeignException e) {

            throw new SpotifyApiException("Erro ao consultar álbuns no Spotify");
        }

        return saveAlbumList(albumsClientResponse.items(), artist);
    }


    public List<AlbumResponse> findAlbumsByArtist(Long artistId){

        validateArtistExistsById(artistId);

        List<Album> albumList = albumRepository.findByArtistId(artistId);

        return albumMapper.toAlbumResponseList(albumList);
    }

    public ArtistResponse findById(Long id){

        Artist artist = validateArtistExistsById(id);

        return artistMapper.toArtistResponse(artist);

    }

    public List<ArtistResponse> findAll() {

        List<Artist> artists = artistRepository.findAll();

        return artistMapper.toArtistResponseList(artists);
    }

    private void validateArtistAlreadyExists(String spotifyId) {

        if (artistRepository.existsBySpotifyId(spotifyId)) {
            throw new ArtistAlreadyExistsException("Artista já cadastrado");
        }
    }

    private Artist validateArtistExists(String spotifyArtistId) {

        return artistRepository.findBySpotifyId(spotifyArtistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artista não encontrado"));
    }

    private Artist validateArtistExistsById(Long artistId) {

        return artistRepository.findById(artistId).orElseThrow(() ->
                new ArtistNotFoundException("Artista não encontrado"));
    }

    private List<AlbumResponse> saveAlbumList(List<AlbumClientResponse> albums, Artist artist) {

        List<AlbumResponse> albumsResponse = new ArrayList<>();

        for (AlbumClientResponse albumClientResponse : albums) {

            if (albumRepository.existsBySpotifyId(albumClientResponse.id())) {
                continue;
            }

            Album album = albumMapper.toAlbum(albumClientResponse, artist);

            Album savedAlbum = albumRepository.save(album);

            AlbumResponse albumResponse = albumMapper.toAlbumResponse(savedAlbum);

            albumsResponse.add(albumResponse);
        }

        return albumsResponse;
    }

}
