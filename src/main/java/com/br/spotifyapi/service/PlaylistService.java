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

    private static final String ONLY_SIMPLE_CHARACTERS = "[a-zA-ZÀ-ÿ0-9 ]+";

    public PlaylistResponse createPlaylist (PlaylistRequest playlistRequest) throws BusinessExceptions{

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

    public TrackResponse addTrack (Long playlistId, String  spotifyTrackId) throws BusinessExceptions{

        Playlist playlist = validatePlaylistExistsById(playlistId);

        validateTrackAlreadyExists(spotifyTrackId, playlistId);

        String accessToken = spotifyAuthService.getAccessToken();

        String authorization = "Bearer " + accessToken;

        TrackClientResponse trackClientResponse;

        try {
            trackClientResponse = spotifyClient.getTrack(spotifyTrackId, authorization);

        } catch (FeignException e) {

            throw new SpotifyApiException("Erro ao consultar música no Spotify");
        }

        Track track = trackMapper.toTrack(trackClientResponse, playlist);

        Track savedTrack = trackRepository.save(track);

        return trackMapper.toTrackResponse(savedTrack);
    }

    private void validateNameCharacters(String name) throws BusinessExceptions {

        if (name == null || name.isBlank()) {
            throw new BusinessExceptions("Nome não pode ser vazio ou nulo");
        }

        if (name.length() > 50 || !name.matches(ONLY_SIMPLE_CHARACTERS)) {
            throw new BusinessExceptions(
                    "Nome deve ter no máximo 50 caracteres e não pode conter caracteres especiais");
        }
    }

    private void validatePlaylistAlreadyExists (String name) throws BusinessExceptions{

        if (playlistRepository.existsByName(name)){
            throw new BusinessExceptions ("Já existe playlist com esse nome");
        }
    }

    private void validateDescription (String description) throws BusinessExceptions{

        if (description == null || description.isBlank()) {
            throw new BusinessExceptions("Descrição não pode ser vazia ou nula");
        }

        if (description.length() > 120){
            throw new BusinessExceptions("Tamanho da descrição inválido");
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

    private void validateTrackAlreadyExists(String spotifyTrackId, Long playlistId) throws BusinessExceptions {

        if (trackRepository.existsBySpotifyIdAndPlaylistId(spotifyTrackId, playlistId)) {
            throw new BusinessExceptions("Música já cadastrada nesta playlist");
        }
    }
}

