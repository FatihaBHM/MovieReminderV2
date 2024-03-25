package org.lafabrique_epita.application.dto.movie_get;

import org.lafabrique_epita.application.dto.movie_post.CommentDto;
import org.lafabrique_epita.application.dto.movie_post.GenreMovieDto;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;

public class MovieGetResponseDtoMapper {

    public static MovieGetResponseDTO convertToMovieDto(MovieEntity movieEntity) {
        return new MovieGetResponseDTO(
                movieEntity.getId(),
                movieEntity.getIdTmdb(),
                movieEntity.getTitle(),
                movieEntity.getDuration(),
                movieEntity.getOverview(),
                movieEntity.getBackdropPath(),
                movieEntity.getScore(),
                movieEntity.getGenres().stream()
                        .map(MovieGetResponseDtoMapper::convertToGenreDto)
                        .toList(),
                movieEntity.getReleaseDate(),
                movieEntity.getComments().stream()
                        .map(MovieGetResponseDtoMapper::convertToCommentDto)
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
