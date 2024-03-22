package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;

public interface IPlaylistMovieService {
    void save(PlayListMovieEntity playListMovieEntity);

    PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID);
}
