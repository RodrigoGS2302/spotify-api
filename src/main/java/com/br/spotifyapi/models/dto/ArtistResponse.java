package com.br.spotifyapi.models.dto;

public record ArtistResponse(

        Long id,
        String spotifyId,
        String name,
        String spotifyUrl,
        Integer popularity

) {
}
