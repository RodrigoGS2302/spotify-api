package com.br.spotifyapi.models.mapper;

import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public Artist toArtist(ArtistClientResponse response) {


        Artist artist = new Artist();

        artist.setSpotifyId(response.id());
        artist.setName(response.name());
        artist.setPopularity(response.popularity());
        artist.setSpotifyUrl(response.externalUrls().spotify());

        return artist;
    }

    public ArtistResponse toArtistResponse(Artist artist) {

        return new ArtistResponse(
                artist.getId(),
                artist.getSpotifyId(),
                artist.getName(),
                artist.getSpotifyUrl(),
                artist.getPopularity()
        );
    }
}
