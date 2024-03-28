package org.lafabrique_epita.application.dto.media.movie_post;

import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.ArrayList;
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
        List<GenreEntity> genres = getGenres(moviePostDto);
        movie.setGenres(genres);
        movie.setReleaseDate(moviePostDto.releaseDate());
        movie.setComments(new ArrayList<>());

        return movie;
    }

    private static List<GenreEntity> getGenres(MoviePostDto moviePostDto) {
        if (moviePostDto.genres() == null) {
            return List.of();
        }
        return moviePostDto.genres()
                .stream()
                .map(genre -> new GenreEntity(null, genre.id(), genre.name()))
                .toList();
    }
}
