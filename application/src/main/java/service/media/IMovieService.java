package service.media;

import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;

public interface IMovieService {
    MovieEntity save(MovieEntity movie);

    List<MovieEntity> getAll();
}
