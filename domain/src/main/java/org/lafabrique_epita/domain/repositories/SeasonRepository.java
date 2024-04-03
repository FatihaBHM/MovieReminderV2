package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.Optional;

public interface SeasonRepository {
    SeasonEntity save(SeasonEntity seasonEntity);

    Optional<SeasonEntity> findByIdTmdb(Long idTmdbSeason);

    void delete(SeasonEntity season);
}
