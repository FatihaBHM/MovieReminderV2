package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SeasonRepository {
    SeasonEntity save(SeasonEntity seasonEntity);


    List<SeasonEntity> saveAll(Collection<SeasonEntity> seasonEntity);

    Optional<SeasonEntity> findByIdTmdb(Long idTmdbSeason);
}
