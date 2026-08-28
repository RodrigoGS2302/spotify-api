package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de uma música da playlist")
public record TrackResponse(

        @Schema(
                description = "ID da música no banco de dados",
                example = "1"
        )
        Long id,

        @Schema(
                description = "ID da música no Spotify",
                example = "4uLU6hMCjMI75M1A2tKUQC"
        )
        String spotifyId,

        @Schema(
                description = "Nome da música",
                example = "Never Gonna Give You Up"
        )
        String name

) {
}