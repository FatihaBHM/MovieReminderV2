package org.lafabrique_epita.application.dto.movie_post;

import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

@Slf4j
public class MoviePostDtoResponseMapper {

    private MoviePostDtoResponseMapper() {
    }

    public static MoviePostResponseDto convertToMovieDto(MovieEntity movieEntity) {
        log.warn("Converting MovieEntity to MoviePostResponseDto: {}", movieEntity);
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
                movieEntity.getReleaseDate(),
                movieEntity.getComments().stream()
                        .map(MoviePostDtoResponseMapper::convertToCommentDto)
                        .toList()
        );
    }

    private static GenreMovieDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreMovieDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }

    private static CommentDto convertToCommentDto(CommentEntity commentEntity) {
        return new CommentDto(
                commentEntity.getId(),
                commentEntity.getDescription(),
                commentEntity.getScore()
        );
    }

}
