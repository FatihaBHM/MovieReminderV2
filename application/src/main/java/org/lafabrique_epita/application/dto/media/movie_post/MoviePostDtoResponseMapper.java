package org.lafabrique_epita.application.dto.media.movie_post;

import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

public class MoviePostDtoResponseMapper {

    private MoviePostDtoResponseMapper() {
    }

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
                movieEntity.getReleaseDate(),
                movieEntity.getComments().stream()
                        .map(MoviePostDtoResponseMapper::convertToCommentDto)
                        .toList()
        );
    }

    private static GenreDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreDto(
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
