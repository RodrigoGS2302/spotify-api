package com.br.spotifyapi.models.dto;

public record AlbumResponse(

        Long id,
        String spotifyId,
        String name,
        String releaseDate,
        Integer totalTracks,
        String spotifyUrl

) {
}
