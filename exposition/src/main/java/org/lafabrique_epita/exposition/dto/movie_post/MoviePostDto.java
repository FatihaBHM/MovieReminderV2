package org.lafabrique_epita.exposition.dto.movie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record MoviePostDto(

        @JsonProperty("id_tmdb")
        @NotNull
        @PositiveOrZero
        Long idTmdb,

        @NotNull
        @NotBlank
        String title,

        @PositiveOrZero
        Integer duration,

        String overview,

        @JsonProperty("poster_path")
        String backdropPath,

        Float score,

        List<GenreMoviePostDto> genres,

        @JsonProperty("release_date")
        LocalDate releaseDate

) {
}
