package com.br.spotifyapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackClientResponse(

        @JsonProperty("id")
        String spotifyId,

        String name

) {
}