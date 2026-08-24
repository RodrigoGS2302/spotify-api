package com.br.spotifyapi.controller;

import com.br.spotifyapi.models.dto.AlbumResponse;
import com.br.spotifyapi.models.dto.ArtistResponse;
import com.br.spotifyapi.service.ArtistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
@Tag(
        name = "Artistas",
        description = "Endpoints para gerenciamento de artistas e álbuns"
)
public class ArtistController implements ArtistInterface {

    private final ArtistService artistService;

    @Override
    @PostMapping("/{spotifyId}")
    public ResponseEntity<ArtistResponse> saveArtist(@PathVariable String spotifyId) {

        ArtistResponse artistResponse = artistService.saveArtist(spotifyId);

        return ResponseEntity.status(HttpStatus.CREATED).body(artistResponse);
    }

    @Override
    @PostMapping("/{spotifyId}/albums")
    public ResponseEntity<List<AlbumResponse>> saveAlbums(@PathVariable String spotifyId) {

        List<AlbumResponse> albumResponses = artistService.saveAlbums(spotifyId);

        return ResponseEntity.ok(albumResponses);
    }

    @Override
    @GetMapping("/{artistId}/albums")
    public ResponseEntity<List<AlbumResponse>> findAlbumsByArtist(@PathVariable Long artistId) {

        List<AlbumResponse> albumResponses = artistService.findAlbumsByArtist(artistId);

        return ResponseEntity.ok(albumResponses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable Long id) {

        ArtistResponse artistResponse = artistService.findById(id);

        return ResponseEntity.ok(artistResponse);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ArtistResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<ArtistResponse> artistResponses = artistService.findAll(page, size, direction);

        return ResponseEntity.ok(artistResponses);
    }
}