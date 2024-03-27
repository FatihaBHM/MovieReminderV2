package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.EpisodeEntity;

import java.util.List;

public interface EpisodeRepository {
    EpisodeEntity save(EpisodeEntity episodeEntity);

    List<EpisodeEntity> saveAll(List<EpisodeEntity> episodeEntity);
}
