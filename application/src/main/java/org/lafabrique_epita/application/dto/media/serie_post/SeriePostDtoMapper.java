package org.lafabrique_epita.application.dto.media.serie_post;

import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;

import java.util.List;

public class SeriePostDtoMapper {

    private SeriePostDtoMapper() {
    }

    public static SerieEntity convertToSerieEntity(SeriePostDto seriePostDto) {
        SerieEntity serie = new SerieEntity();
        serie.setIdTmdb((seriePostDto.idTmdb()));
        serie.setTitle(seriePostDto.title());
        serie.setFirstAirDate(seriePostDto.firstAirDate());
        serie.setOverview(seriePostDto.overview());
        serie.setPosterPath(seriePostDto.posterPath());
        serie.setLastAirDate(seriePostDto.lastAirDate());
        serie.setNumberOfEpisodes(seriePostDto.numberOfEpisodes());
        serie.setNumberOfSeasons(seriePostDto.numberOfSeasons());
        serie.setScore(seriePostDto.score());

        List<GenreEntity> genres = getGenres(seriePostDto);
        serie.setGenres(genres);

        List<SeasonEntity> seasons = getSeasons(seriePostDto);
        serie.setSeasons(seasons);


        return serie;
    }

    private static List<SeasonEntity> getSeasons(SeriePostDto seriePostDto) {
        if (seriePostDto.seasons() == null) {
            return List.of();
        }
        return seriePostDto.seasons()
                .stream()
                .map(SeasonPostDtoMapper::convertToSeasonEntity)
                .toList();
    }

    private static List<GenreEntity> getGenres(SeriePostDto seriePostDto) {
        if (seriePostDto.genres() == null) {
            return List.of();
        }
        return seriePostDto.genres()
                .stream()
                .map(genre -> new GenreEntity(null, genre.id(), genre.name()))
                .toList();
    }
}
