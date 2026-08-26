package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository <Playlist, Long > {

    Optional<Playlist> findPlaylistByNameIgnoreCase (String name);

    boolean existsByName(String name);

    List<Playlist> findAllByOrderByCreatedAtAsc();

}
