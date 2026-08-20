package com.br.spotifyapi.controller;


import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/{spotifyId}")
    public ResponseEntity<ArtistResponse> saveArtist(@PathVariable String spotifyId){

        ArtistResponse  artistResponse = artistService.saveArtist(spotifyId);

        return ResponseEntity.status(HttpStatus.CREATED).body(artistResponse);
    }

    @PostMapping("/{spotifyId}/albums")
    public ResponseEntity<List<AlbumResponse>> saveAlbums(@PathVariable String spotifyId) {

        List<AlbumResponse> albumResponses = artistService.saveAlbums(spotifyId);

        return ResponseEntity.ok(albumResponses);
    }

    @GetMapping("/{artistId}/albums")
    public ResponseEntity<List<AlbumResponse>> findAlbumsByArtist(@PathVariable Long artistId){

        List<AlbumResponse> albumResponses = artistService.findAlbumsByArtist(artistId);

        return ResponseEntity.ok(albumResponses);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable Long id){

        ArtistResponse artistResponse = artistService.findById(id);

        return ResponseEntity.ok(artistResponse);
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> findAll (){

        List<ArtistResponse> artistResponses = artistService.findAll();

        return ResponseEntity.ok(artistResponses);
    }

    @GetMapping("/ranking")
    public  ResponseEntity<List<ArtistResponse>> findRanking(){

        List<ArtistResponse> artistResponses = artistService.findRanking();

        return ResponseEntity.ok(artistResponses);

    }


}
