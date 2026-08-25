package com.br.spotifyapi.exceptions;

public class InvalidPlaylistNameException extends RuntimeException {
    public InvalidPlaylistNameException(String message) {
        super(message);
    }
}
