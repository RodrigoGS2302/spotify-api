package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Resposta padrão de erro da API")
public record StandardError(

        @Schema(
                description = "Momento em que o erro ocorreu",
                example = "2026-08-21T12:00:00Z"
        )
        Instant timestamp,

        @Schema(
                description = "Código HTTP",
                example = "404"
        )
        Integer status,

        @Schema(
                description = "Descrição do status HTTP",
                example = "Not Found"
        )
        String error,

        @Schema(
                description = "Mensagem detalhada do erro",
                example = "Artista não encontrado"
        )
        String message,

        @Schema(
                description = "Endpoint que gerou o erro",
                example = "/artists/999"
        )
        String path

) {
}