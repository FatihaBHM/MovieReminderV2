package org.lafabrique_epita.infrastructure.playlist_movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayListMovieRepositoryAdapter implements PlayListMovieRepository {

    private final PlayListMovieJPARepositoryPort playListMovieJPARepository;

    public PlayListMovieRepositoryAdapter(PlayListMovieJPARepositoryPort playListMovieJPARepository) {
        this.playListMovieJPARepository = playListMovieJPARepository;
    }


    @Override
    public PlayListMovieEntity save(PlayListMovieEntity playListMovieEntity) {
       return this.playListMovieJPARepository.save(playListMovieEntity);
    }

    @Override
    public Optional<PlayListMovieEntity> findByMovieIdAndUserId(PlayListMovieID playListMovieID) {
        return this.playListMovieJPARepository.findByMovieIdAndUserId(playListMovieID.getMovieId(), playListMovieID.getUserId());
    }

    @Override
    public PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public List<MovieEntity> findMoviesByUserId(UserEntity user) {
        return this.playListMovieJPARepository.findMoviesByUserId(user);
    }

    @Override
    public boolean existsByMovieIdAndUserId(Long movieId, Long userId) {
        return this.playListMovieJPARepository.existsByMovieIdAndUserId(movieId, userId)    ;
    }

}
