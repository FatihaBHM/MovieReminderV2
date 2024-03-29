package org.lafabrique_epita.application.dto.media.serie_get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lafabrique_epita.domain.entities.EpisodeEntity;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonGetResponseDto {
    private String overview;
    @JsonProperty("id_tmdb")
    private Long idTmdb;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("season_number")
    private int seasonNumber;
    private List<EpisodeGetResponseDto> episodes;

    public SeasonGetResponseDto(String overview, Long idTmdb, String posterPath, int seasonNumber) {
        this(overview, idTmdb, posterPath, seasonNumber, null);
    }
}
