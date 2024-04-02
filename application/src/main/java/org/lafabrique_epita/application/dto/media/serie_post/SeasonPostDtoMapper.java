package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.ArrayList;
import java.util.List;

public class SeasonPostDtoMapper {

    private SeasonPostDtoMapper() {
    }

    public static SeasonEntity convertToSeasonEntity(SeasonPostDto seasonPostDto) {
        SeasonEntity season = new SeasonEntity();
        season.setOverview(seasonPostDto.overview());
        season.setIdTmdb(seasonPostDto.idTmdb());
        season.setPosterPath(seasonPostDto.posterPath());
        season.setSeasonNumber(seasonPostDto.seasonNumber());
        season.setEpisodes(convertEpisodesToEntity(seasonPostDto.episodes()));
        return season;

    }

    private static List<EpisodeEntity> convertEpisodesToEntity(List<EpisodePostDto> episodes) {
        List<EpisodeEntity> episodeEntities = new ArrayList<>();
        for (EpisodePostDto episode : episodes) {
            episodeEntities.add(EpisodePostDtoMapper.convertToEntity(episode));
        }
        return episodeEntities;
    }


    public static SeasonPostDto convertToDto(SeasonEntity seasonEntity) {
        return new SeasonPostDto(
                seasonEntity.getOverview(),
                seasonEntity.getIdTmdb(),
                seasonEntity.getPosterPath(),
                seasonEntity.getSeasonNumber(),
                convertEpisodesToDto(seasonEntity.getEpisodes())
        );
    }

    private static List<EpisodePostDto> convertEpisodesToDto(List<EpisodeEntity> episodes) {
        List<EpisodePostDto> episodePostDtos = new ArrayList<>();
        for (EpisodeEntity episode : episodes) {
            episodePostDtos.add(EpisodePostDtoMapper.convertToDto(episode));
        }
        return episodePostDtos;
    }
}
