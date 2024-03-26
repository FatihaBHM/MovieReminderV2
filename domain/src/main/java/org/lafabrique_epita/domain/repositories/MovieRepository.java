package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> findAll();

    Optional<MovieEntity> findByIdTmdb(Long idTmdb);

    void deleteById(Long id);
}
