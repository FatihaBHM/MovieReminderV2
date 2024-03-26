package org.lafabrique_epita.infrastructure.season;

import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.lafabrique_epita.infrastructure.movie.MovieJPARepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class SeasonRepositoryAdapter implements SeasonRepository {

    private final MovieJPARepositoryPort movieJPARepository;

    public SeasonRepositoryAdapter(MovieJPARepositoryPort movieJPARepository) {
        this.movieJPARepository = movieJPARepository;
    }
}
