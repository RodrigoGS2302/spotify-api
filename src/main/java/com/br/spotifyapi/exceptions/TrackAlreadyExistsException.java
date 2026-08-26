package com.br.spotifyapi.exceptions;

public class TrackAlreadyExistsException extends RuntimeException {
    public TrackAlreadyExistsException(String message) {
        super(message);
    }
}
