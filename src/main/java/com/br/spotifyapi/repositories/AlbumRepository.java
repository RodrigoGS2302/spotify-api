package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long>{

    List<Album> findByArtistId(Long artistId);

    boolean existsBySpotifyId(String spotifyId);

}
