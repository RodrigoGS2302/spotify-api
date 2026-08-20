package com.br.spotifyapi.models.dto;

import java.time.Instant;

public record StandardError(

        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path

) {
}
