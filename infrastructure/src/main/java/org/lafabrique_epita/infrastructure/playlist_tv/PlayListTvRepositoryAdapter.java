package org.lafabrique_epita.infrastructure.playlist_tv;

import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.lafabrique_epita.infrastructure.movie.MovieJPARepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListTvRepositoryAdapter implements PlayListTvRepository {

    private final MovieJPARepositoryPort movieJPARepository;

    public PlayListTvRepositoryAdapter(MovieJPARepositoryPort movieJPARepository) {
        this.movieJPARepository = movieJPARepository;
    }

}
