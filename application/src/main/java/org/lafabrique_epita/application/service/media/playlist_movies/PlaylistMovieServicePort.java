package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.movie_post.MoviePostDto;
import org.lafabrique_epita.application.dto.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.MovieException;

import java.util.List;

public interface PlaylistMovieServicePort {
    MoviePostResponseDto save(MoviePostDto moviePostDto, UserEntity user) throws MovieException;

    List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user);


    MoviePostResponseDto findByUserAndByMovie(Long movieId, Long userId);

    boolean updateFavorite(Long idMovie, Integer favorite, Long idUser) throws MovieException;

    void updateStatus(Long movieId, StatusEnum status, Long userId) throws MovieException;

    void delete(Long movieId, int i, Long userId) throws MovieException;
}
