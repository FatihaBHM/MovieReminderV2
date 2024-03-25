package org.lafabrique_epita.infrastructure.serie;

import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.lafabrique_epita.infrastructure.movie.MovieJPARepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class SerieRepositoryAdapter implements SerieRepository {

    private final MovieJPARepositoryPort movieJPARepository;

    public SerieRepositoryAdapter(MovieJPARepositoryPort movieJPARepository) {
        this.movieJPARepository = movieJPARepository;
    }
}
