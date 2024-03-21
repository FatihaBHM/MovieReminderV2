package org.lafabrique_epita.exposition.dto.moviePost;

import lombok.RequiredArgsConstructor;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;


import java.util.List;

@RequiredArgsConstructor
public class MoviePostDtoResponseMapper {
    public static MovieEntity convertToMovieEntity(MoviePostResponseDto moviePostResponseDto) {
        MovieEntity movie = new MovieEntity();

        movie.setId(moviePostResponseDto.id());

        movie.setTitle(moviePostResponseDto.title());

        movie.setDuration(moviePostResponseDto.duration());

        movie.setOverview(moviePostResponseDto.overview());

        movie.setBackdropPath(moviePostResponseDto.backdropPath());

        movie.setScore(moviePostResponseDto.score());

        List<GenreEntity> genres = moviePostResponseDto.genres().stream()
                .map(genreMoviePostDto -> new GenreEntity(
                        genreMoviePostDto.id(),
                        genreMoviePostDto.idTmdb(),
                        genreMoviePostDto.name(),
                        null,
                        null))
                .toList();
        movie.setGenres(genres);

        movie.setReleaseDate(moviePostResponseDto.releaseDate());

        return movie;
    }





}
