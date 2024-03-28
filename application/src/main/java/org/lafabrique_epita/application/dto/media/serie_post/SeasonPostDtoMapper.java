package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.List;

public class SeasonPostDtoMapper {

    private SeasonPostDtoMapper() {
    }

    public  static SeasonEntity convertToSeasonEntity(SeasonPostDto seasonPostDto) {
        SeasonEntity season = new SeasonEntity();
        season.setOverview(seasonPostDto.overview());
        season.setIdTmdb(seasonPostDto.idTmdb());
        season.setPosterPath(seasonPostDto.posterPath());
        season.setSeasonNumber(seasonPostDto.seasonNumber());
        List<EpisodeEntity> episodes = getEpisodes(seasonPostDto);
        season.setEpisodes(episodes);

        return season;

    }

    private static List<EpisodeEntity> getEpisodes(SeasonPostDto seasonPostDto) {
        if (seasonPostDto.episodes() == null) {
            return List.of();
        }
        return seasonPostDto.episodes()
                .stream()
                .map(EpisodePostDtoMapper::convertToEpisodeEntity)
                .toList();
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
