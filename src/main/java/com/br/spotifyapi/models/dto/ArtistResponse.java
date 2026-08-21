package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com os dados do artista")
public record ArtistResponse(

        @Schema(
                description = "ID interno do artista no banco",
                example = "1"
        )
        Long id,

        @Schema(
                description = "ID do artista no Spotify",
                example = "45Yz90pqjzEdJzpEQg1eII"
        )
        String spotifyId,

        @Schema(
                description = "Nome do artista",
                example = "Eminem"
        )
        String name,

        @Schema(
                description = "URL do artista no Spotify",
                example = "https://open.spotify.com/artist/..."
        )
        String spotifyUrl,

        @Schema(
                description = "Índice de popularidade do artista no Spotify",
                example = "95"
        )
        Integer popularity

) {
}