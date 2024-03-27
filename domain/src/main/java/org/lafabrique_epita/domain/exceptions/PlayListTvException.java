package org.lafabrique_epita.domain.exceptions;

import org.springframework.http.HttpStatus;

public class PlayListTvException extends MovieReminderException{

    public PlayListTvException(String message) {super(message, HttpStatus.BAD_REQUEST);}

    public PlayListTvException(String message, HttpStatus status) {super(message, status);}
}
