package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository <Playlist, Long > {
}
