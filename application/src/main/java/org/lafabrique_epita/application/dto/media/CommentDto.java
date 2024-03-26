package org.lafabrique_epita.application.dto.media;


public record CommentDto(
        Long id,
        String description,
        float score
) {
}
