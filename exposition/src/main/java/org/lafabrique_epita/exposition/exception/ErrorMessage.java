package org.lafabrique_epita.exposition.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public record ErrorMessage(
        Integer status,
        @JsonProperty("error_message")
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
