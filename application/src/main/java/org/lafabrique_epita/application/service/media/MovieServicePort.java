package org.lafabrique_epita.application.service.media;

import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.exceptions.MovieException;

import java.util.List;

public interface MovieServicePort {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> getAll();

    MovieGetResponseDTO findMovieByIdTmdb(Long idTmdb) throws MovieException;
}
