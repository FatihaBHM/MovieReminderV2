package org.lafabrique_epita.application.dto.movie_post;


public record CommentDto(
        Long id,
        String description,
        float score
) {
}
