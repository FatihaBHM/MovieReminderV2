package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class PlayListMovieException extends MovieReminderException {
    public PlayListMovieException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public PlayListMovieException(String message, HttpStatus status) {
        super(message, status);
    }
}
