package org.lafabrique_epita.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MovieReminderException extends Exception{

    private HttpStatus httpStatus;

    public MovieReminderException(String message) {
        super(message);
    }
    public MovieReminderException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
