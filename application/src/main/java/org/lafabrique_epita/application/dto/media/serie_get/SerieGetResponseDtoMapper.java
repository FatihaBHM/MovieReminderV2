package org.lafabrique_epita.application.dto.media.serie_get;

import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;

public class SerieGetResponseDtoMapper {

    public static SerieGetResponseDto convertToSerieDto(SerieEntity serieEntity) {
        return new SerieGetResponseDto(
                serieEntity.getId(),
                serieEntity.getFirstAirDate(),
                serieEntity.getIdTmdb(),
                serieEntity.getOverview(),
                serieEntity.getPosterPath(),
                serieEntity.getLastAirDate(),
                serieEntity.getTitle(),
                serieEntity.getNumberOfEpisodes(),
                serieEntity.getNumberOfSeasons(),
                serieEntity.getScore(),
                serieEntity.getComments().stream().map(SerieGetResponseDtoMapper::convertToCommentDto).toList(),
                serieEntity.getGenres().stream().map(SerieGetResponseDtoMapper::convertToGenreDto).toList()
        );
    }

    private static CommentDto convertToCommentDto(CommentEntity commentEntity) {
        return new CommentDto(
                commentEntity.getId(),
                commentEntity.getDescription(),
                commentEntity.getScore()
        );
    }

    private static GenreDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }

}
