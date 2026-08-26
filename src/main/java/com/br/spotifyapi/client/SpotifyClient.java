package com.br.spotifyapi.client;


import com.br.spotifyapi.client.dto.ArtistClientResponse;
import com.br.spotifyapi.client.dto.TrackClientResponse;
import com.br.spotifyapi.models.dto.AlbumsClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "spotifyClient",
        url = "https://api.spotify.com"
)
public interface SpotifyClient {

    @GetMapping("/v1/artists/{id}")
    ArtistClientResponse getArtist (@PathVariable ("id") String id, @RequestHeader("Authorization") String authorization
    );

    @GetMapping("/v1/artists/{id}/albums")
    AlbumsClientResponse getAlbums(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization
    );

    @GetMapping("/v1/tracks/{id}")
    TrackClientResponse getTrack(@PathVariable("id") String spotifyTrackId,
                                 @RequestHeader("Authorization") String authorization
    );
}
