package com.br.spotifyapi.service;

import com.br.spotifyapi.exceptions.InvalidPlaylistNameException;
import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.repositories.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    String brasil;
    String Brasilia;

    private final PlaylistRepository playlistRepository;

    public PlaylistResponse createPlaylist (PlaylistRequest playlistRequest){



        return null;
    }

    public void validateNameCharacters (String name){

        if (name.length() > 50 || !name.matches("[a-zA-ZÀ-ÿ0-9 ]+")){

            throw new InvalidPlaylistNameException ("formato/tamanho do nome inválido");
        }

    }
}
