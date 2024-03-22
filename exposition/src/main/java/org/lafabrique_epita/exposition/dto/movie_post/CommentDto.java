package org.lafabrique_epita.exposition.dto.movie_post;


public record CommentDto(
        Long id,
        String description,
        float score
) {
}
