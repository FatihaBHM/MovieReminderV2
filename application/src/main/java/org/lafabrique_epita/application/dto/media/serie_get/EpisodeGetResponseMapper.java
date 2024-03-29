package org.lafabrique_epita.application.dto.media.serie_get;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;

public class EpisodeGetResponseMapper {

    private EpisodeGetResponseMapper() {
    }

    public static EpisodeGetResponseDto convertToDto(EpisodeEntity episodeEntity, boolean favorite, StatusEnum status) {
        return new EpisodeGetResponseDto(
                episodeEntity.getAirDate(),
                episodeEntity.getEpisodeNumber(),
                episodeEntity.getTitle(),
                episodeEntity.getOverview(),
                episodeEntity.getIdTmdb(),
                episodeEntity.getDuration(),
                episodeEntity.getSeasonNumber(),
                episodeEntity.getImagePath(),
                favorite,
                status
        );
    }
}
