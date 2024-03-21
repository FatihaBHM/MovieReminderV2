package org.lafabrique_epita.exposition.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreMoviePostResponseDto(
        Long id, //A enlever?
        Long idTmdb,
        @NotNull
        @NotBlank
        String name
) {}
