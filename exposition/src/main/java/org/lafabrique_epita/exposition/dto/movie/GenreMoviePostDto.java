package org.lafabrique_epita.exposition.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreMoviePostDto(
        Long id, //A enlever?

        @NotNull
        @NotBlank
        String name
) {}
