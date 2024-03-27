package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class SerieException extends MovieReminderException{

    public SerieException(String message) {
        super(message);
    }

    public SerieException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
