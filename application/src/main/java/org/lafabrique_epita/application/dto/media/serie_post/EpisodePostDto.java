package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record EpisodePostDto(
        @JsonProperty("air_date")
        LocalDate airDate,

        @JsonProperty("episode_number")
        int episodeNumber,

        String title,

        String overview,

        @JsonProperty("id_tmdb")
        Long idTmdb,

        int duration,

        @JsonProperty("season_number")
        int seasonNumber,

        @JsonProperty("image_path")
        String imagePath
) {
}
