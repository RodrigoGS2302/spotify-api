package com.br.spotifyapi.exceptions;

public class ArtistAlreadyExistsException extends RuntimeException {

    public ArtistAlreadyExistsException(String message) {
        super(message);
    }
}
