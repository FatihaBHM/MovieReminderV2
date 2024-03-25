package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;

public interface PlayListMovieRepository {
    PlayListMovieEntity save(PlayListMovieEntity playListMovieEntity);

    PlayListMovieEntity findByMovieIdAndUserId(PlayListMovieID playListMovieID);

    PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId);

    List<MovieEntity> findMoviesByUserId(UserEntity user);

    boolean existsByMovieIdAndUserId(Long movieId, Long userId);
}
