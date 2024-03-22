package org.lafabrique_epita.exposition.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorMessage(
        @Schema(
                description = "HTTP status code",
                example = "400"
        )
        Integer status,

        @Schema(
                description = "Error message",
                example = """
                        {
                            "id_tmdb": "ne doit pas être nul"
                        }
                        """
        )
        Object errorMessage
) {
}
