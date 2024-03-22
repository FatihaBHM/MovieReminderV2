package org.lafabrique_epita.infrastructure.playlist_movie;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
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

    @Override
    public PlayListMovieEntity findByMovieIdAndUserId(PlayListMovieID playListMovieID) {
        return this.playListMovieJPARepository.findByMovieIdAndUserId(playListMovieID.getMovieId(), playListMovieID.getUserId());
    }

    @Override
    public PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId) {
        // TODO: Implement this method
        return null;
    }
}
