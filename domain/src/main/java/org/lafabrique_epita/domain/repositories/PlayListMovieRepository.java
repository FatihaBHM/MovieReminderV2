package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;

public interface PlayListMovieRepository {
    void save(PlayListMovieEntity playListMovieEntity);

    PlayListMovieEntity findByMovieIdAndUserId(PlayListMovieID playListMovieID);

    PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId);

    //List<MovieEntity> findAllMoviesByUser(PlayListMovieID playListMovieID);

    List<MovieEntity> findMoviesByUserId(UserEntity user);
}
