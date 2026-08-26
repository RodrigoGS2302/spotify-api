package com.br.spotifyapi.service;

import com.br.spotifyapi.client.SpotifyClient;
import com.br.spotifyapi.client.dto.TrackClientResponse;
import com.br.spotifyapi.exceptions.*;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private  final PlaylistMapper playlistMapper;
    private final SpotifyClient spotifyClient;
    private final SpotifyAuthService spotifyAuthService;
    private  final TrackRepository trackRepository;
    private final TrackMapper trackMapper;


    public PlaylistResponse createPlaylist (PlaylistRequest playlistRequest){

        validateNameCharacters(playlistRequest.name());

        validatePlaylistAlreadyExists(playlistRequest.name());

        validateDescription(playlistRequest.description());

        Playlist playlist = playlistMapper.toPlaylist(playlistRequest);

        Playlist savedPlaylist = playlistRepository.save(playlist);

        return playlistMapper.toPlaylistResponse(savedPlaylist);
    }

    public PlaylistResponse findPlaylistByName (String name){

        Playlist playlist = validatePlaylistExists(name);

        return playlistMapper.toPlaylistResponse(playlist);
    }

    public List<PlaylistResponse> findAllPlaylist (){

        List<Playlist> playlists = playlistRepository.findAllByOrderByCreatedAtAsc();

        return playlistMapper.toPlaylistResponseList(playlists);
    }

    public TrackResponse addTrack (Long playlistId, String  spotifyTrackId){

        Playlist playlist = validatePlaylistExistsById(playlistId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        TrackClientResponse trackClientResponse;

        try {
            trackClientResponse = spotifyClient.getTrack(spotifyTrackId, authorization);

        } catch (FeignException e) {

            throw new SpotifyApiException("Erro ao consultar música no Spotify");
        }
        validateTrackAlreadyExists(spotifyTrackId, playlistId);

        Track track = trackMapper.toTrack(trackClientResponse, playlist);

        Track savedTrack = trackRepository.save(track);

        return trackMapper.toTrackResponse(savedTrack);
    }

    private void validateNameCharacters (String name){

        if (name == null || name.isBlank()) {
            throw new InvalidPlaylistNameException("Nome não pode ser vazio ou nulo");
        }

        if (name.length() > 50 || !name.matches("[a-zA-ZÀ-ÿ0-9 ]+")){

            throw new InvalidPlaylistNameException ("Formato/tamanho do nome inválido");
         }
    }

    private void validatePlaylistAlreadyExists (String name){

        if (playlistRepository.existsByName(name)){
            throw new PlaylistAlreadyExistsException ("Já existe playlist com esse nome");
        }
    }

    private void validateDescription (String description){

        if (description == null || description.isBlank()) {
            throw new InvalidDescriptionException("Descrição não pode ser vazia ou nula");
        }

        if (description.length() > 120){
            throw new InvalidDescriptionException("Tamanho da descrição inválido");
        }
    }

    private Playlist validatePlaylistExists(String name) {

        return playlistRepository.findPlaylistByNameIgnoreCase(name)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist não encontrada"));
    }

    private Playlist validatePlaylistExistsById(Long playlistId) {

        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist não encontrada"));
    }

    private void validateTrackAlreadyExists(String spotifyTrackId, Long playlistId) {

        if (trackRepository.existsBySpotifyIdAndPlaylistId(spotifyTrackId, playlistId)) {
            throw new TrackAlreadyExistsException("Música já cadastrada nesta playlist");
        }
    }


}

