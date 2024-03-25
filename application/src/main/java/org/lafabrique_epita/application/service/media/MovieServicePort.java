package org.lafabrique_epita.application.service.media;

import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;

public interface MovieServicePort {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> getAll();
}
