package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.models.dto.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArtistInterface {

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
    ResponseEntity<ArtistResponse> saveArtist(
            @Parameter(
                    description = "ID do artista no Spotify",
                    example = "45Yz90pqjzEdJzpEQg1eII"
            )
            String spotifyId
    );

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
    ResponseEntity<List<AlbumResponse>> saveAlbums(
            @Parameter(
                    description = "ID do artista no Spotify",
                    example = "45Yz90pqjzEdJzpEQg1eII"
            )
            String spotifyId
    );

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
    ResponseEntity<List<AlbumResponse>> findAlbumsByArtist(
            @Parameter(
                    description = "ID interno do artista no banco de dados",
                    example = "1"
            )
            Long artistId
    );

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
    ResponseEntity<ArtistResponse> findById(
            @Parameter(
                    description = "ID interno do artista no banco de dados",
                    example = "1"
            )
            Long id
    );

    @Operation(
            summary = "Listar artistas com paginação",
            description = "Retorna os artistas cadastrados de forma paginada, com ordenação por nome em ordem ascendente ou descendente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Página de artistas retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Direção de ordenação inválida",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    ResponseEntity<Page<ArtistResponse>> findAll(

            @Parameter(
                    description = "Número da página. A primeira página é 0",
                    example = "0"
            )
            int page,

            @Parameter(
                    description = "Quantidade de artistas por página",
                    example = "5"
            )
            int size,

            @Parameter(
                    description = "Direção da ordenação pelo nome: asc ou desc",
                    example = "asc"
            )
            String direction
    );
}