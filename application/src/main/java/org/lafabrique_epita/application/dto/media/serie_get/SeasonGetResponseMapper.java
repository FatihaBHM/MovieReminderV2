package org.lafabrique_epita.application.dto.media.serie_get;

import org.lafabrique_epita.domain.entities.SeasonEntity;

public class SeasonGetResponseMapper {

    private SeasonGetResponseMapper() {
    }

    public static SeasonGetResponseDto convertToDto(SeasonEntity seasonEntity) {
        return new SeasonGetResponseDto(
                seasonEntity.getOverview(),
                seasonEntity.getIdTmdb(),
                seasonEntity.getPosterPath(),
                seasonEntity.getSeasonNumber(),
                seasonEntity.getEpisodes().stream().map(EpisodeGetResponseMapper::convertToDto).toList()
        );
    }
}
