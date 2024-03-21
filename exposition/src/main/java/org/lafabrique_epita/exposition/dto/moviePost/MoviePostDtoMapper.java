package org.lafabrique_epita.exposition.dto.moviePost;

import lombok.RequiredArgsConstructor;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

import java.util.List;

@RequiredArgsConstructor
public class MoviePostDtoMapper {

    public static MovieEntity convertToMovieEntity(MoviePostDto moviePostDto) {
        MovieEntity movie = new MovieEntity();
        movie.setIdTmdb(moviePostDto.id());
        movie.setTitle(moviePostDto.titre());
        movie.setDuration(moviePostDto.duration());
        movie.setOverview(moviePostDto.resume());
        movie.setBackdropPath(moviePostDto.imageLandscape());
        movie.setScore(moviePostDto.score());
        //créer une liste de GenreEntity en partant du genreDto
        List<GenreEntity> genres = moviePostDto.genres()
                .stream()
                .map(genre -> new GenreEntity(null, genre.id(), genre.name(), null, null))
                .toList();
        movie.setGenres(genres);
        movie.setReleaseDate(moviePostDto.date());

        return movie;
    }
}
