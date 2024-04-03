package org.lafabrique_epita.application.service.media.serie;

import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.EpisodeException;
import org.lafabrique_epita.domain.exceptions.SerieException;

import java.util.List;

public interface EpisodeServicePort {
    List<EpisodeEntity> saveAll(List<EpisodePostDto> episodePostDtos, Long idTmdbSeason) throws SerieException;

    EpisodePostDto save(EpisodeEntity episodeEntity, UserEntity user) throws EpisodeException;

}
