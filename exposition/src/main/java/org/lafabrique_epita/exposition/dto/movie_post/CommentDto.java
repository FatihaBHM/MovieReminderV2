package org.lafabrique_epita.exposition.dto.movie_post;


import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDto(
        @Schema(
                description = "The id of the comment",
                example = "1"
        )
        Long id,

        @Schema(
                description = "The description of the comment",
                example = "This movie is awesome"
        )
        String description,

        @Schema(
                description = "The score of the comment",
                example = "9.3"
        )
        float score
) {
}
