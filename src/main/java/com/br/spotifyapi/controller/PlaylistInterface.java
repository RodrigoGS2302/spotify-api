package com.br.spotifyapi.controller;

import com.br.spotifyapi.exceptions.BusinessExceptions;
import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.dto.StandardError;
import com.br.spotifyapi.models.dto.TrackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlaylistInterface {

    @Operation(
            summary = "Criar playlist",
            description = "Cria uma nova playlist com nome e descrição informados pelo usuário e salva no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Playlist criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nome ou descrição inválidos",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Playlist com esse nome já cadastrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    ResponseEntity<PlaylistResponse> createPlaylist(
            PlaylistRequest playlistRequest
    )throws BusinessExceptions;

    @Operation(
            summary = "Buscar playlist por nome",
            description = "Retorna uma playlist cadastrada a partir do nome informado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlist encontrada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            )
    })
    ResponseEntity<PlaylistResponse> findPlaylistByName(
            @Parameter(
                    description = "Nome da playlist",
                    example = "Treino Pesado"
            )
            String name
    );

    @Operation(
            summary = "Listar todas as playlists",
            description = "Retorna todas as playlists cadastradas, ordenadas pela data de criação da mais antiga para a mais recente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlists retornadas com sucesso"
            )
    })
    ResponseEntity<List<PlaylistResponse>> findAllPlaylist();

    @Operation(
            summary = "Adicionar música à playlist",
            description = "Busca uma música na API do Spotify pelo Spotify ID e adiciona à playlist informada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Música adicionada à playlist com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = StandardError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Música já adicionada à playlist",
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
    ResponseEntity<TrackResponse> addTrack(

            @Parameter(
                    description = "ID da playlist no banco de dados",
                    example = "1"
            )
            Long playlistId,

            @Parameter(
                    description = "ID da música no Spotify",
                    example = "4uLU6hMCjMI75M1A2tKUQC"
            )
            String spotifyTrackId
    ) throws BusinessExceptions;
}