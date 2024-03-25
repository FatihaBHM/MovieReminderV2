package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;

public interface IPlaylistMovieService {
    void save(PlayListMovieEntity playListMovieEntity);

    PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID);

    List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user);




}
