package com.br.spotifyapi.models.mapper;

import com.br.spotifyapi.models.dto.AlbumClientResponse;
import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.entites.Album;
import com.br.spotifyapi.models.entites.Artist;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public Album toAlbum(AlbumClientResponse albumClientResponse, Artist artist) {

        Album album = new Album();

        album.setSpotifyId(albumClientResponse.id());
        album.setName(albumClientResponse.name());
        album.setReleaseDate(albumClientResponse.releaseDate());
        album.setTotalTracks(albumClientResponse.totalTracks());
        album.setSpotifyUrl(albumClientResponse.externalUrls().spotify());

        album.setArtist(artist);

        return album;
    }

    public AlbumResponse toAlbumResponse(Album album) {

        return new AlbumResponse(
                album.getId(),
                album.getSpotifyId(),
                album.getName(),
                album.getReleaseDate(),
                album.getTotalTracks(),
                album.getSpotifyUrl()
        );
    }

}
