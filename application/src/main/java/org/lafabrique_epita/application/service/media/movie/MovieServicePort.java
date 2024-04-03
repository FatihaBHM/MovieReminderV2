package org.lafabrique_epita.application.service.media.movie;

import org.lafabrique_epita.domain.entities.MovieEntity;

public interface MovieServicePort {
    MovieEntity save(MovieEntity movie);
}
