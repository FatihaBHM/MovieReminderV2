package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.EpisodeEntity;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository {
    EpisodeEntity save(EpisodeEntity episodeEntity);

    List<EpisodeEntity> saveAll(List<EpisodeEntity> episodeEntity);

    Optional<EpisodeEntity> findByIdTmdb(Long idTmdb);

    void delete(EpisodeEntity episode);
}
