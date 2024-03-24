package org.lafabrique_epita.infrastructure.season;

import org.lafabrique_epita.infrastructure.movie.MovieJPARepository;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SeasonRepositoryAdapter implements SeasonRepository {

    private final SeasonJPARepository seasonJPARepository;

    public SeasonRepositoryAdapter(SeasonJPARepository seasonJPARepository) {
        this.seasonJPARepository = seasonJPARepository;
    }
}
