package com.br.spotifyapi.models.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PlaylistResponse(

        Long id,

        String name,

        String description,

        LocalDateTime createdAt,

       List<TrackResponse> track

) {
}
