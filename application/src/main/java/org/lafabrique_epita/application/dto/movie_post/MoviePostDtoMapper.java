package org.lafabrique_epita.application.dto.movie_post;

import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;

public class MoviePostDtoMapper {

    private MoviePostDtoMapper() {
    }

    public static MovieEntity convertToMovieEntity(MoviePostDto moviePostDto) {
        MovieEntity movie = new MovieEntity();
        movie.setIdTmdb(moviePostDto.idTmdb());
        movie.setTitle(moviePostDto.title());
        movie.setDuration(moviePostDto.duration());
        movie.setOverview(moviePostDto.overview());
        movie.setBackdropPath(moviePostDto.backdropPath());
        movie.setScore(moviePostDto.score());
        //créer une liste de GenreEntity en partant du genreDto
        List<GenreEntity> genres = moviePostDto.genres()
                .stream()
                .map(genre -> new GenreEntity(null, genre.id(), genre.name(), null, null))
                .toList();
        movie.setGenres(genres);
        movie.setReleaseDate(moviePostDto.releaseDate());

        return movie;
    }
}
