package org.lafabrique_epita.exposition.dto.movie_post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreMoviePostDto(
        /*
        Request => idTmdb
        Response => idTmdb
         */
        Long id,

        @NotNull
        @NotBlank
        String name
) {}
