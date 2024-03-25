package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListTvEntity;

public interface PlayListTvRepository {

    void save(PlayListTvEntity playListTvEntity);
}
