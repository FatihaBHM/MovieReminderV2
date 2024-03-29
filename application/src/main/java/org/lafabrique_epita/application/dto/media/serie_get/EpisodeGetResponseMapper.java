package org.lafabrique_epita.application.dto.media.serie_get;

import org.lafabrique_epita.domain.entities.EpisodeEntity;

public class EpisodeGetResponseMapper {

    private EpisodeGetResponseMapper() {
    }

    public static EpisodeGetResponseDto convertToDto(EpisodeEntity episodeEntity) {
        return new EpisodeGetResponseDto(
                episodeEntity.getAirDate(),
                episodeEntity.getEpisodeNumber(),
                episodeEntity.getTitle(),
                episodeEntity.getOverview(),
                episodeEntity.getIdTmdb(),
                episodeEntity.getDuration(),
                episodeEntity.getSeasonNumber(),
                episodeEntity.getImagePath()
        );
    }
}
