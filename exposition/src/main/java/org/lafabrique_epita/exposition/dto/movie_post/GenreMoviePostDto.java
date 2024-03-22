package org.lafabrique_epita.exposition.dto.movie_post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreMoviePostDto(
        /*
        Request => idTmdb
        Response => idTmdb
         */
        @Schema(
                description = "The id of the genre in the TMDB database",
                example = "1"
        )
        Long id,

        @Schema(
                description = "The name of the genre",
                example = "Action"
        )
        @NotNull
        @NotBlank
        String name
) {}
