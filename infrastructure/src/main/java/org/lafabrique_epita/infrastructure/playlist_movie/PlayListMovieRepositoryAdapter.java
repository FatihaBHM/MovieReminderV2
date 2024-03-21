package org.lafabrique_epita.infrastructure.playlist_movie;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListMovieRepositoryAdapter implements PlayListMovieRepository {

        private final PlayListMovieJPARepository playListMovieJPARepository;

    public PlayListMovieRepositoryAdapter(PlayListMovieJPARepository playListMovieJPARepository) {
        this.playListMovieJPARepository = playListMovieJPARepository;
    }


    @Override
    public void save(PlayListMovieEntity playListMovieEntity) {
        this.playListMovieJPARepository.save(playListMovieEntity);
    }
}
