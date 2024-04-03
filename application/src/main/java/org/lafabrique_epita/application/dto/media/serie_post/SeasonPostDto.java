package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO pour la création d'une saison
 */
public record SeasonPostDto(
        String overview,

        @JsonProperty("id_tmdb")
        @NotNull(message = "L'id TMDB ne doit pas être nul")
        @Positive(message = "L'id TMDB doit être positif")
        Long idTmdb,

        @JsonProperty("poster_path")
        String posterPath,

        @PositiveOrZero(message = "Le nombre d'épisodes doit être positif ou nul")
        @JsonProperty("season_number")
        Integer seasonNumber
) {
}
