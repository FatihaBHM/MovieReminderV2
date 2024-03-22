package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;

public interface PlayListMovieRepository {
    void save(PlayListMovieEntity playListMovieEntity);

    PlayListMovieEntity findByMovieIdAndUserId(PlayListMovieID playListMovieID);

    PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId);
}
