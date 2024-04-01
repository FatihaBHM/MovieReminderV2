package org.lafabrique_epita.application.dto.media.movie_get;

import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;

public class MovieGetResponseDtoMapper {

    private MovieGetResponseDtoMapper() {
    }

    public static MovieGetResponseDTO convertToMovieDto(MovieEntity movieEntity, PlayListMovieEntity playListMovieEntity) {
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
                playListMovieEntity.getStatus().ordinal(),
                movieEntity.getCreatedDate(),
                movieEntity.getComments().stream()
                        .map(MovieGetResponseDtoMapper::convertToCommentDto)
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
