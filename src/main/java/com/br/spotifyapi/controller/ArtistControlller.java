package com.br.spotifyapi.controller;


import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistControlller {

    private final ArtistService artistService;

    @PostMapping("/{spotifyId}")
    public ResponseEntity<ArtistResponse> saveArtist(@PathVariable String spotifyId){

        ArtistResponse  artistResponse = artistService.saveArtist(spotifyId);

        return ResponseEntity.ok(artistResponse);
    }

    @PostMapping("/{spotifyId}/albums")
    public ResponseEntity<List<AlbumResponse>> saveAlbums(@PathVariable String spotifyId) {

        List<AlbumResponse> response = artistService.saveAlbums(spotifyId);

        return ResponseEntity.ok(response);
    }

}
