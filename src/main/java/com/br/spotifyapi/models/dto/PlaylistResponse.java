package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Dados retornados de uma playlist")
public record PlaylistResponse(

        @Schema(
                description = "ID da playlist no banco de dados",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome da playlist",
                example = "Rock Classics"
        )
        String name,

        @Schema(
                description = "Descrição da playlist",
                example = "Clássicos do rock para ouvir no dia a dia."
        )
        String description,

        @Schema(
                description = "Data e hora de criação da playlist",
                example = "2026-08-25T14:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Músicas adicionadas à playlist"
        )
        List<TrackResponse> tracks

) {
}
