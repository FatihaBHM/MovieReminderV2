package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record SeasonPostDto(
        String overview,

        @JsonProperty("id_tmdb")
        @NotNull(message = "L'id TMDB ne doit pas être nul")
        @NotBlank(message = "L'id TMDB ne doit pas être vide")
        @Positive(message = "L'id TMDB doit être positif")
        Long idTmdb,

        @JsonProperty("poster_path")
        String posterPath,

        @PositiveOrZero(message = "Le nombre d'épisodes doit être positif ou nul")
        @JsonProperty("season_number")
        int seasonNumber
) {
}
