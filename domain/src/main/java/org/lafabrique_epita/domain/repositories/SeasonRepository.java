package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.SeasonEntity;

import java.util.Collection;
import java.util.List;

public interface SeasonRepository {
    SeasonEntity save(SeasonEntity seasonEntity);


    List<SeasonEntity> saveAll(Collection<SeasonEntity> seasonEntity);
}
