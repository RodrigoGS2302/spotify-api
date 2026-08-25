package com.br.spotifyapi.models.mapper;

import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.entites.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistMapper {

    private final TrackMapper trackMapper;

    public Playlist toPlaylist (PlaylistRequest playlistRequest){

        Playlist playlist = new Playlist();

        playlist.setName( playlistRequest.name());
        playlist.setDescription(playlistRequest.description());

        return playlist;

    }

    public PlaylistResponse toPlaylistResponse (Playlist playlist){

      return new PlaylistResponse(
              playlist.getId(),
              playlist.getName(),
              playlist.getDescription(),
              playlist.getCreatedAt(),
              trackMapper.toTrackResponseList(playlist.getTracks())

      );

    }
}
