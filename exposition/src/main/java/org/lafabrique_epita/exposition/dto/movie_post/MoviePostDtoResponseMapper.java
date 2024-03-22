package org.lafabrique_epita.exposition.dto.movie_post;

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

    private static GenreMoviePostDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreMoviePostDto(
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
