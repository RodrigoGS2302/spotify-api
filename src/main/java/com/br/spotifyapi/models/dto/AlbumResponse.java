package com.br.spotifyapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com os dados do álbum")
public record AlbumResponse(

        @Schema(
                description = "ID interno do álbum no banco",
                example = "1"
        )
        Long id,

        @Schema(
                description = "ID do álbum no Spotify",
                example = "4aawyAB9vmqN3uQ7FjRGTy"
        )
        String spotifyId,

        @Schema(
                description = "Nome do álbum",
                example = "The Eminem Show"
        )
        String name,

        @Schema(
                description = "Data de lançamento do álbum",
                example = "2002-05-26"
        )
        String releaseDate,

        @Schema(
                description = "Quantidade total de faixas",
                example = "20"
        )
        Integer totalTracks,

        @Schema(
                description = "URL do álbum no Spotify",
                example = "https://open.spotify.com/album/..."
        )
        String spotifyUrl

) {
}