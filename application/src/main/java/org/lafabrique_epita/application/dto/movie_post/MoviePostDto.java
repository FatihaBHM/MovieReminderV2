package org.lafabrique_epita.application.dto.movie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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

        List<GenreMovieDto> genres,

        @JsonProperty("release_date")
        LocalDate releaseDate

) {
}
