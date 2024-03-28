package org.lafabrique_epita.application.dto.media.movie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record MoviePostDto(

        @JsonProperty("id_tmdb")
        @NotNull(message = "L'id TMDB ne doit pas être nul")
        @Positive(message = "L'id TMDB doit être positif")
        Long idTmdb,

        @NotNull(message = "Le titre ne doit pas être nul")
        @NotBlank(message = "Le titre ne doit pas être vide")
        String title,

        @PositiveOrZero(message = "La durée doit être positive ou nulle")
        Integer duration,

        String overview,

        @JsonProperty("poster_path")
        String backdropPath,

        Float score,

        List<GenreDto> genres,

        @JsonProperty("release_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate releaseDate

) {
}
