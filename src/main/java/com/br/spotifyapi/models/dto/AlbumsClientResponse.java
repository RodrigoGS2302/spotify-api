package com.br.spotifyapi.models.dto;

import com.br.spotifyapi.client.dto.AlbumClientResponse;

import java.util.List;

public record AlbumsClientResponse(
        List<AlbumClientResponse> items
) {
}