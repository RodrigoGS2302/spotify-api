package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.dto.StandardError;
import com.br.spotifyapi.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@Tag(
        name = "Artistas",
        description = "Endpoints para gerenciamento de artistas e álbuns"
)
public class ArtistController {

    private final ArtistService artistService;

    @Operation(
            summary = "Cadastrar artista",
            description = "Busca um artista na API do Spotify pelo Spotify ID e salva no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Artista cadastrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Artista já cadastrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Erro ao consultar a API do Spotify",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    @PostMapping("/{spotifyId}")
    public ResponseEntity<ArtistResponse> saveArtist(
            @Parameter(
                    description = "ID do artista no Spotify",
                    example = "45Yz90pqjzEdJzpEQg1eII"
            )
            @PathVariable String spotifyId) {

        ArtistResponse artistResponse = artistService.saveArtist(spotifyId);

        return ResponseEntity.status(HttpStatus.CREATED).body(artistResponse);
    }

    @Operation(
            summary = "Cadastrar álbuns do artista",
            description = "Busca os álbuns do artista na API do Spotify e salva os que ainda não estão cadastrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Álbuns processados com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artista não encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Erro ao consultar a API do Spotify",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    @PostMapping("/{spotifyId}/albums")
    public ResponseEntity<List<AlbumResponse>> saveAlbums(
            @Parameter(
                    description = "ID do artista no Spotify",
                    example = "45Yz90pqjzEdJzpEQg1eII"
            )
            @PathVariable String spotifyId) {

        List<AlbumResponse> albumResponses = artistService.saveAlbums(spotifyId);

        return ResponseEntity.ok(albumResponses);
    }

    @Operation(
            summary = "Buscar álbuns por artista",
            description = "Retorna os álbuns cadastrados de determinado artista"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Álbuns encontrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artista não encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    @GetMapping("/{artistId}/albums")
    public ResponseEntity<List<AlbumResponse>> findAlbumsByArtist(
            @Parameter(
                    description = "ID interno do artista no banco de dados",
                    example = "1"
            )
            @PathVariable Long artistId) {

        List<AlbumResponse> albumResponses = artistService.findAlbumsByArtist(artistId);

        return ResponseEntity.ok(albumResponses);
    }

    @Operation(
            summary = "Buscar artista por ID",
            description = "Retorna um artista cadastrado pelo ID interno do banco de dados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Artista encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artista não encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(
            @Parameter(
                    description = "ID interno do artista no banco de dados",
                    example = "1"
            )
            @PathVariable Long id) {

        ArtistResponse artistResponse = artistService.findById(id);

        return ResponseEntity.ok(artistResponse);
    }

    @Operation(
            summary = "Listar artistas",
            description = "Retorna todos os artistas cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de artistas retornada com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> findAll() {

        List<ArtistResponse> artistResponses = artistService.findAll();

        return ResponseEntity.ok(artistResponses);
    }
}