package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record EpisodePostDto(
        @JsonProperty("air_date")
        LocalDate airDate,

        @JsonProperty("episode_number")
        int episodeNumber,

        @NotNull(message = "Le titre ne doit pas être nul")
        @NotBlank(message = "La titre ne doit pas être vide")
        String title,

        String overview,

        @JsonProperty("id_tmdb")
        @NotNull(message = "L'id TMDB ne doit pas être nul")
        @Positive(message = "L'id TMDB doit être positif")
        Long idTmdb,

        int duration,

        @PositiveOrZero(message = "Le numéro de la saison doit être positif ou nul")
        @JsonProperty("season_number")
        int seasonNumber,

        @JsonProperty("image_path")
        String imagePath
) {
}
