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


    @ExceptionHandler(BusinessExceptions.class)
    public ResponseEntity<StandardError> playlistNotFound(
            BusinessExceptions e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                e,
                request
        );
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<StandardError> artistNotFound(
            ArtistNotFoundException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Artist not found",
                e,
                request
        );
    }

    @ExceptionHandler(ArtistAlreadyExistsException.class)
    public ResponseEntity<StandardError> artistAlreadyExists(
            ArtistAlreadyExistsException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Artist already exists",
                e,
                request
        );
    }

    @ExceptionHandler(AlbumAlreadyExistsException.class)
    public ResponseEntity<StandardError> albumAlreadyExists(
            AlbumAlreadyExistsException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Album already exists",
                e,
                request
        );
    }

    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<StandardError> invalidSortDirection(
            InvalidSortDirectionException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Invalid sort direction",
                e,
                request
        );
    }

    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<StandardError> spotifyApiError(
            SpotifyApiException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_GATEWAY,
                "Bad Gateway",
                e,
                request
        );
    }



    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<StandardError> playlistNotFound(
            PlaylistNotFoundException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Playlist not found",
                e,
                request
        );
    }

    @ExceptionHandler(TrackAlreadyExistsException.class)
    public ResponseEntity<StandardError> trackAlreadyExists(
            TrackAlreadyExistsException e,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Track already exists",
                e,
                request
        );
    }

    private ResponseEntity<StandardError> buildError(
            HttpStatus status,
            String error,
            Exception e,
            HttpServletRequest request) {

        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }
}