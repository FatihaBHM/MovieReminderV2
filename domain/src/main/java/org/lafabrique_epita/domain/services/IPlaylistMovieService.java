package org.lafabrique_epita.domain.services;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.exceptions.PlayListMovieException;

public interface IPlaylistMovieService {
    void save(PlayListMovieEntity playListMovieEntity) throws PlayListMovieException;

    PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID);
}
