package com.br.spotifyapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistClientResponse(

        String id,

        String name,

        Integer popularity,

        @JsonProperty("external_urls")
        ExternalUrlsResponse externalUrls

) {
}
