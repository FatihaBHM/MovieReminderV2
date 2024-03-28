package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class EpisodeException extends MovieReminderException{

    public EpisodeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public EpisodeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
