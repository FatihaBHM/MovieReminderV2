package org.lafabrique_epita.exposition.dto.moviePost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreMoviePostResponseDto(
        Long idTmdb,
        @NotNull
        @NotBlank
        String name
) {}
