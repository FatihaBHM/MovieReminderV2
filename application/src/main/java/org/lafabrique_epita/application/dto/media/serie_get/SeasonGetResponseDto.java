package org.lafabrique_epita.application.dto.media.serie_get;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SeasonGetResponseDto(
        String overview,

        @JsonProperty("id_tmdb")
        Long idTmdb,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("season_number")
        int seasonNumber,

        List<EpisodeGetResponseDto> episodes
) {
}
