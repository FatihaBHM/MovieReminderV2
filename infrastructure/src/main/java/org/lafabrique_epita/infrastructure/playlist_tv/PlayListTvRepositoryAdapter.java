package org.lafabrique_epita.infrastructure.playlist_tv;

import org.lafabrique_epita.infrastructure.movie.MovieJPARepository;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListTvRepositoryAdapter implements PlayListTvRepository {

    private final PlayListTvJPARepository playListTvJPARepository;


    public PlayListTvRepositoryAdapter(PlayListTvJPARepository playListTvJPARepository) {
        this.playListTvJPARepository = playListTvJPARepository;
    }
}
