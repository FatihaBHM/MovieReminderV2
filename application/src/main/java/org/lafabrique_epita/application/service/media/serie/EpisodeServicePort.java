package org.lafabrique_epita.application.service.media.serie;

import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;

public interface EpisodeServicePort {
    EpisodeEntity save(EpisodePostDto episodePostDto, Long idTmdbSeason) throws SerieException;
}
