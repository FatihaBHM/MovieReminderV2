package org.lafabrique_epita.application.dto.media;


import jakarta.validation.constraints.Size;

public record CommentDto(
        Long id,

        @Size(min = 10, max = 1000)
        String description,

        float score
) {
}
