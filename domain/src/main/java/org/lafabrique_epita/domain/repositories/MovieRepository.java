package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;

public interface MovieRepository {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> findAll();
}
