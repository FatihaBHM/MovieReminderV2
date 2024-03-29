package org.lafabrique_epita.application.dto.media.serie_get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lafabrique_epita.domain.enums.StatusEnum;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeGetResponseDto {
    @JsonProperty("air_date")
    private LocalDate airDate;
    @JsonProperty("episode_number")
    private int episodeNumber;
    private String title;
    private String overview;
    @JsonProperty("id_tmdb")
    private Long idTmdb;
    private int duration;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("image_path")
    private String imagePath;
    private boolean favorite;
    private StatusEnum status;

    public EpisodeGetResponseDto(LocalDate airDate, int episodeNumber, String title, String overview, Long idTmdb, int duration, int seasonNumber, String imagePath) {
        this(airDate, episodeNumber, title, overview, idTmdb, duration, seasonNumber, imagePath, false, null);
    }

}
