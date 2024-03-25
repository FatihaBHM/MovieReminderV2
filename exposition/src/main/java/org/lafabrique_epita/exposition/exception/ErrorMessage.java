package org.lafabrique_epita.exposition.exception;

import org.springframework.http.HttpStatus;

public record ErrorMessage(
        Integer status,
        Object errorMessage
) {
    public static String format(HttpStatus status, String errorMessage) {
        return String.format("""
                {
                    "status": %s,
                    "errorMessage": %s
                }
                """, status, errorMessage);
    }
}
