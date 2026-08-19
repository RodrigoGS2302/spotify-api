package com.br.spotifyapi.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_artist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spotifyId;

    private String name;

    private String spotifyUrl;

    private Integer popularity;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();
}