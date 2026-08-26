package com.br.spotifyapi.exceptions;

import com.br.spotifyapi.models.dto.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<StandardError> artistNotFound(
            ArtistNotFoundException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ArtistAlreadyExistsException.class)
    public ResponseEntity<StandardError> artistAlreadyExists(
            ArtistAlreadyExistsException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<StandardError> spotifyApiError(
            SpotifyApiException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_GATEWAY;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<StandardError> invalidSortDirection(
            InvalidSortDirectionException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidPlaylistNameException.class)
    public ResponseEntity<StandardError> invalidPlaylistName(
            InvalidPlaylistNameException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Invalid playlist name",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<StandardError> invalidDescription(
            InvalidDescriptionException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Invalid description",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(PlaylistAlreadyExistsException.class)
    public ResponseEntity<StandardError> playlistAlreadyExists(
            PlaylistAlreadyExistsException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Playlist already exists",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<StandardError> playlistNotFound(
            PlaylistNotFoundException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Playlist not found",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TrackAlreadyExistsException.class)
    public ResponseEntity<StandardError> trackAlreadyExists(
            TrackAlreadyExistsException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Track already exists",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

}