package org.lafabrique_epita.exposition.exception;

import org.springframework.http.HttpStatus;

public record ErrorMessage(
        Integer status,
        Object errorMessage
) {
    public static String format(HttpStatus status, String errorMessage) {
        return String.format("""
                {
                    STATUS: %s,
                    "error_message": %s
                }
                """, status, errorMessage);
    }
}
