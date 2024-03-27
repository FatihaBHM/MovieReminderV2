package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;

import java.util.ArrayList;
import java.util.List;

public class SeriePostDtoMapper {

    private SeriePostDtoMapper() {
    }

    public static SerieEntity convertToSerieEntity(SeriePostDto seriePostDto) {
        SerieEntity serie = new SerieEntity();
        serie.setId(seriePostDto.id());
        serie.setIdTmdb((seriePostDto.idTmdb()));
        serie.setTitle(seriePostDto.title());
        serie.setFirstAirDate(seriePostDto.firstAirDate());
        serie.setOverview(seriePostDto.overview());
        serie.setPosterPath(seriePostDto.posterPath());
        serie.setLastAirDate(seriePostDto.lastAirDate());
        serie.setNumberOfEpisodes(seriePostDto.numberOfEpisodes());
        serie.setNumberOfSeasons(seriePostDto.numberOfSeasons());
        serie.setScore(seriePostDto.score());

        List<GenreEntity> genres = seriePostDto.genres()
                .stream()
                .map(genre -> new GenreEntity(null, genre.id() , genre.name()))
                .toList();
        serie.setGenres(genres);

        List<SeasonEntity> seasons = seriePostDto.seasons()
                .stream()
                .map(SeasonPostDtoMapper::convertToSeasonEntity )
                .toList();
        serie.setSeasons(seasons);


        return serie;
    }
}
