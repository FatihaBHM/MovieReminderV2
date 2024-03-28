package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.SerieEntity;

import java.util.List;
import java.util.Optional;

public interface SerieRepository {

    SerieEntity save(SerieEntity serie);

    Optional<SerieEntity> findByIdTmdb(Long aLong);

    List<SerieEntity> findAll();

    Optional<SerieEntity> findById(Long serieId);

    void delete(SerieEntity serie);
}
