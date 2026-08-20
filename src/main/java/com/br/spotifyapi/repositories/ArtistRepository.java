package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository <Artist, Long> {

    boolean existsBySpotifyId(String spotifyId);

    Optional<Artist> findBySpotifyId(String spotifyId);

    List<Artist> findAllByOrderByPopularityDesc();


}
