package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> findAll();

    Optional<MovieEntity> findById(Long id);

    Optional<MovieEntity> findByIdTmdb(Long idTmdb);
}
