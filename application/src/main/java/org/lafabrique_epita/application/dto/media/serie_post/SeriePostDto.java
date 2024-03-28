package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


public record SeriePostDto(


        @JsonProperty("first_air_date")
        LocalDate firstAirDate,

        @JsonProperty("id_tmdb")
        @NotNull(message = "L'id TMDB ne doit pas être nul")
        @Positive(message = "L'id TMDB doit être positif")
        Long idTmdb,

        String overview,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("last_air_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate lastAirDate,

        String title,

        @PositiveOrZero(message = "Le nombre d'épisodes doit être positif ou nul")
        @JsonProperty("number_of_episodes")
        int numberOfEpisodes,

        @PositiveOrZero(message = "Le nombre de saisons doit être positif ou nul")
        @JsonProperty("number_of_seasons")
        int numberOfSeasons,

        float score,

        List<GenreDto> genres,

        List<SeasonPostDto> seasons
) {
}
