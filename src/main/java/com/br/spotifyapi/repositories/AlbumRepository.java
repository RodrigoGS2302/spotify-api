package com.br.spotifyapi.repositories;

import com.br.spotifyapi.models.entites.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>{
}
