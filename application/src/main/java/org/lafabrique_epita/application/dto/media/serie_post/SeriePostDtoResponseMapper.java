package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.application.dto.media.CommentDtoMapper;
import org.lafabrique_epita.application.dto.media.GenreDtoMapper;
import org.lafabrique_epita.domain.entities.SerieEntity;

import java.util.stream.Collectors;

public class SeriePostDtoResponseMapper {

    private SeriePostDtoResponseMapper() {
    }

    public static SeriePostResponseDto convertToSerieDto(SerieEntity serieEntity) {

        return new SeriePostResponseDto(
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
                serieEntity.getGenres().stream()
                        .map(GenreDtoMapper::convertToDto)
                        .collect(Collectors.toList()),
                serieEntity.getComments().stream()
                        .map(CommentDtoMapper::convertToDto)
                        .collect(Collectors.toList()),
                serieEntity.getSeasons().stream()
                        .map(SeasonPostDtoMapper::convertToDto).collect(Collectors.toList())
        );
    }


}
