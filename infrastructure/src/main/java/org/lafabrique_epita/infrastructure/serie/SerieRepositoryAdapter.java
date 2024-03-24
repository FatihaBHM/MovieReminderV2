package org.lafabrique_epita.infrastructure.serie;

import org.lafabrique_epita.infrastructure.movie.MovieJPARepository;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SerieRepositoryAdapter implements SerieRepository {

    private final SerieJPARepository serieJPARepository;

    public SerieRepositoryAdapter(SerieJPARepository serieJPARepository) {
        this.serieJPARepository = serieJPARepository;
    }
}
