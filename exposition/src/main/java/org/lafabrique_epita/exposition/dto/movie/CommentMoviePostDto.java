package org.lafabrique_epita.exposition.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentMoviePostDto(

        @NotBlank
        @NotNull
        String comment,
        Float score
) {}
