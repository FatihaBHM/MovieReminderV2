package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.ArrayList;
import java.util.List;

public class SeasonPostDtoMapper {
    public  static SeasonEntity convertToSeasonEntity(SeasonPostDto seasonPostDto) {
        SeasonEntity season = new SeasonEntity();
        season.setOverview(seasonPostDto.overview());
        season.setIdTmdb(seasonPostDto.idTmdb());
        season.setPosterPath(seasonPostDto.posterPath());
        season.setSeasonNumber(seasonPostDto.seasonNumber());
        List<EpisodeEntity> episodes = seasonPostDto.episodes()
                .stream()
                .map(EpisodePostDtoMapper::convertToEpisodeEntity)
                .toList();
        season.setEpisodes(episodes);

        return season;

    }

    public static SeasonPostDto convertToDto(SeasonEntity seasonEntity) {
        return new SeasonPostDto(
                seasonEntity.getOverview(),
                seasonEntity.getIdTmdb(),
                seasonEntity.getPosterPath(),
                seasonEntity.getSeasonNumber(),
                seasonEntity.getEpisodes().stream()
                        .map(EpisodePostDtoMapper::convertToDto).toList()
        );
    }
}
