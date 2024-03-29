package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.SeasonEntity;

public class SeasonPostDtoMapper {

    private SeasonPostDtoMapper() {
    }

    public static SeasonEntity convertToSeasonEntity(SeasonPostDto seasonPostDto) {
        SeasonEntity season = new SeasonEntity();
        season.setOverview(seasonPostDto.overview());
        season.setIdTmdb(seasonPostDto.idTmdb());
        season.setPosterPath(seasonPostDto.posterPath());
        season.setSeasonNumber(seasonPostDto.seasonNumber());
        return season;

    }


    public static SeasonPostDto convertToDto(SeasonEntity seasonEntity) {
        return new SeasonPostDto(
                seasonEntity.getOverview(),
                seasonEntity.getIdTmdb(),
                seasonEntity.getPosterPath(),
                seasonEntity.getSeasonNumber()
        );
    }
}
