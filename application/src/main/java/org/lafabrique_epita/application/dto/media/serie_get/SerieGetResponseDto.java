package org.lafabrique_epita.application.dto.media.serie_get;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeasonPostDto;

import java.time.LocalDate;
import java.util.List;

public record SerieGetResponseDto(
        Long id,
        @JsonProperty("first_air_date")
        LocalDate firstAirDate,

        @JsonProperty("id_tmdb")
        Long idTmdb,

        String overview,
        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("last_air_date")
        LocalDate lastAirDate,

        @NotNull
        @NotBlank
        String title,

        @JsonProperty("number_of_episode")
        int numberOfEpisodes,

        @JsonProperty("number_of_episode")
        int numberOfSeasons,

        Float score,

        List<CommentDto> comments,

        List<GenreDto> genres,

        List<SeasonPostDto> seasons


) {

}
