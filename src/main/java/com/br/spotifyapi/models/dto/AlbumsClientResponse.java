package com.br.spotifyapi.models.dto;

import java.util.List;

public record AlbumsClientResponse(
        List<AlbumClientResponse> items
) {
}