package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PlayListMovieRepository {
    PlayListMovieEntity save(PlayListMovieEntity playListMovieEntity);

    Optional<PlayListMovieEntity> findByMovieIdAndUserId(PlayListMovieID playListMovieID);

    List<PlayListMovieEntity> findMoviesByUserId(UserEntity user);

    boolean existsByMovieIdAndUserId(Long movieId, Long userId);

    void delete(PlayListMovieEntity playListMovieEntity);

    int countByMovieId(Long movieId);
}
