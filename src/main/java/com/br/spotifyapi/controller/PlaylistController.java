package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.PlaylistRequest;
import com.br.spotifyapi.models.dto.PlaylistResponse;
import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.service.PlaylistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/playlist")
@RequiredArgsConstructor
@Tag(
        name = "Playlist",
        description = "Endpoints para gerenciamento de playlist"
)
public class PlaylistController implements PlaylistInterface {

    private final PlaylistService playlistService;


    @Override
    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist (@RequestBody PlaylistRequest playlistRequest){

        PlaylistResponse playlistResponse = playlistService.createPlaylist(playlistRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(playlistResponse);
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<PlaylistResponse> findPlaylistByName (@PathVariable String name){

        PlaylistResponse playlistResponse = playlistService.findPlaylistByName(name);

        return  ResponseEntity.ok(playlistResponse);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> findAllPlaylist(){

        List<PlaylistResponse> playlistResponse = playlistService.findAllPlaylist();

        return ResponseEntity.ok(playlistResponse);
    }

    @Override
    @PostMapping("/{playlistId}/tracks/{spotifyTrackId}")
    public ResponseEntity<TrackResponse> addTrack(@PathVariable Long playlistId, @PathVariable String spotifyTrackId) {

        TrackResponse trackResponse = playlistService.addTrack(playlistId, spotifyTrackId);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackResponse);
    }



}
