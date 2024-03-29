package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class EpisodeException extends MovieReminderException {
    public EpisodeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
