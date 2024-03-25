package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class MovieException extends MovieReminderException {
    public MovieException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public MovieException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
