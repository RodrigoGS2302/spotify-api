package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de uma playlist")
public record PlaylistRequest(

        @Schema(
                description = "Nome da playlist",
                example = "Rock Classics"
        )
        String name,

        @Schema(
                description = "Descrição do conteúdo da playlist",
                example = "Clássicos do rock para ouvir no dia a dia."
        )
        String description

) {
}
