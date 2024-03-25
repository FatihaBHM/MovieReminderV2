package org.lafabrique_epita.domain.exceptions;


import org.springframework.http.HttpStatus;

public class UserException extends MovieReminderException{
    public UserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
