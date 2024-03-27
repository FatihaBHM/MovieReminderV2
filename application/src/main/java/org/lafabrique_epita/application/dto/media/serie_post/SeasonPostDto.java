package org.lafabrique_epita.application.dto.media.serie_post;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SeasonPostDto(
        String overview,

        @JsonProperty("id_tmdb")
        int idTmdb,

        @JsonProperty("poster_path")
        String posterPath,

        int seasonNumber,

        List<EpisodePostDto> episodes
) {}
