package org.lafabrique_epita.exposition.dto.moviePost;

import lombok.RequiredArgsConstructor;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;


import java.util.List;

@RequiredArgsConstructor
public class MoviePostDtoResponseMapper {
    public static MoviePostResponseDto convertToMovieDto(MovieEntity movieEntity) {

        return new MoviePostResponseDto(
                movieEntity.getId(),
                movieEntity.getIdTmdb(),
                movieEntity.getTitle(),
                movieEntity.getDuration(),
                movieEntity.getOverview(),
                movieEntity.getBackdropPath(),
                movieEntity.getScore(),
                movieEntity.getGenres().stream()
                        .map(MoviePostDtoResponseMapper::convertToGenreDto)
                        .toList(),
                movieEntity.getReleaseDate()
        );
    }

    private static GenreMoviePostResponseDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreMoviePostResponseDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }





}
