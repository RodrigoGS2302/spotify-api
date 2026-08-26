package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository <Track, Long> {

    boolean existsBySpotifyIdAndPlaylistId(String spotifyId, Long playlistId);

}
