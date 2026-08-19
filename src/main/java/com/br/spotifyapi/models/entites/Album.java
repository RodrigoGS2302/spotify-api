package com.br.spotifyapi.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Entity
    @Table(name = "tb_album")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Album {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String spotifyId;

        private String name;

        private String releaseDate;

        private Integer totalTracks;

        private String spotifyUrl;

        @ManyToOne
        @JoinColumn(name = "artist_id")
        private Artist artist;
    }


