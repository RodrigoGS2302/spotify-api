package com.br.spotifyapi.models.dto;


import com.br.spotifyapi.client.dto.ExternalUrlsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AlbumClientResponse(

        String id,

        String name,

        @JsonProperty("release_date")
        String releaseDate,

        @JsonProperty("total_tracks")
        Integer totalTracks,

        @JsonProperty("external_urls")
        ExternalUrlsResponse externalUrls

) {

}
