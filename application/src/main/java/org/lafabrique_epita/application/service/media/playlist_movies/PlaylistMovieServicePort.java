package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.exceptions.PlayListMovieException;

public interface PlaylistMovieServicePort {
    void save(PlayListMovieEntity playListMovieEntity) throws PlayListMovieException;

    PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID);
}
