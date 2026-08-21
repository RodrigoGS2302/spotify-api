package com.br.spotifyapi.models.mapper;

import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.entites.Artist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistMapper {

    public Artist toArtist(ArtistClientResponse response) {


        Artist artist = new Artist();

        artist.setSpotifyId(response.id());
        artist.setName(response.name());
        artist.setSpotifyUrl(response.externalUrls().spotify());

        return artist;
    }

    public ArtistResponse toArtistResponse(Artist artist) {

        return new ArtistResponse(
                artist.getId(),
                artist.getSpotifyId(),
                artist.getName(),
                artist.getSpotifyUrl()
        );
    }

    public List<ArtistResponse> toArtistResponseList(List<Artist> artists) {

        List<ArtistResponse> responses = new ArrayList<>();

        for (Artist artist : artists) {
            responses.add(toArtistResponse(artist));
        }

        return responses;
    }

}
