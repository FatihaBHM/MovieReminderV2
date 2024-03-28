package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.EpisodeEntity;

public class EpisodePostDtoMapper {

    private EpisodePostDtoMapper() {
    }

    public static EpisodeEntity convertToEntity(EpisodePostDto episodePostDto) {
        EpisodeEntity episode = new EpisodeEntity();
        episode.setAirDate(episodePostDto.airDate());
        episode.setEpisodeNumber(episodePostDto.episodeNumber());
        episode.setTitle(episodePostDto.title());
        episode.setOverview(episodePostDto.overview());
        episode.setIdTmdb(episodePostDto.idTmdb());
        episode.setDuration(episodePostDto.duration());
        episode.setSeasonNumber(episodePostDto.seasonNumber());
        episode.setImagePath(episodePostDto.imagePath());

        return episode;
    }

    public static EpisodePostDto convertToDto(EpisodeEntity episodeEntity) {
        return new EpisodePostDto(
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

