package org.lafabrique_epita.application.service.media.playlist_series;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.application.dto.media.serie_get.*;
import org.lafabrique_epita.application.dto.media.serie_post.*;
import org.lafabrique_epita.domain.entities.*;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.EpisodeException;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PlaylistTvServiceAdapter implements PlaylistTvServicePort {
    private final PlayListTvRepository playListTvRepository;
    private final SerieRepository serieRepository;
    private final GenreRepository genreRepository;
    private final SeasonRepository seasonRepository;

    private final EpisodeRepository episodeRepository;

    public PlaylistTvServiceAdapter(PlayListTvRepository playListTvRepository, SerieRepository serieRepository, GenreRepository genreRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
        this.playListTvRepository = playListTvRepository;
        this.serieRepository = serieRepository;
        this.genreRepository = genreRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }


    @Override
    public SeriePostResponseDto save(SeriePostDto seriePostDto, UserEntity user) throws SerieException, EpisodeException {
        // Convertir la DTO en entité
        SerieEntity serie = SeriePostDtoMapper.convertToSerieEntity(seriePostDto);

        // Vérifier si la série existe
        SerieEntity existingSerie = serieRepository.findByIdTmdb(serie.getIdTmdb()).orElse(null);
        if (existingSerie != null) {
            // Utiliser l'entité existante si la série est déjà présente
            serie = existingSerie;
        } else {
            // Gérer les genres
            handleGenres(serie, seriePostDto.genres());

            // Sauvegarder la nouvelle série
            serie = serieRepository.save(serie);
        }

        for (SeasonPostDto seasonDto : seriePostDto.seasons()) {
            SerieEntity finalSerie = serie;
            SeasonEntity season = serie.getSeasons().stream()
                    .filter(s -> s.getIdTmdb().equals(seasonDto.idTmdb()))
                    .findFirst()
                    .orElseGet(() -> {
                        SeasonEntity newSeason = SeasonPostDtoMapper.convertToSeasonEntity(seasonDto);
                        newSeason.setSerie(finalSerie);
                        finalSerie.getSeasons().add(newSeason);
                        return newSeason;
                    });

            for (EpisodePostDto episodeDto : seasonDto.episodes()) {
                season.getEpisodes().stream()
                        .filter(e -> e.getIdTmdb().equals(episodeDto.idTmdb()))
                        .findFirst()
                        .orElseGet(() -> {
                            EpisodeEntity newEpisode = EpisodePostDtoMapper.convertToEntity(episodeDto);
                            newEpisode.setSeason(season);
                            season.getEpisodes().add(newEpisode);
                            return newEpisode;
                        });
            }
        }

        serieRepository.save(serie);

        // Sauvegarder la série dans la playlist de l'utilisateur
        serie.getSeasons()
                .forEach(season -> season.getEpisodes()
                        .forEach(episode -> {
                            PlayListEpisodeEntity playListEpisodeEntity = new PlayListEpisodeEntity();
                            PlayListEpisodeID playListEpisodeID = new PlayListEpisodeID();
                            playListEpisodeID.setEpisodeId(episode.getId());
                            playListEpisodeID.setUserId(user.getId());
                            playListEpisodeEntity.setId(playListEpisodeID);
                            playListEpisodeEntity.setEpisode(episode);
                            playListEpisodeEntity.setUser(user);
                            playListEpisodeEntity.setStatus(StatusEnum.A_REGARDER);
                            playListEpisodeEntity.setFavorite(false);
                            playListTvRepository.save(playListEpisodeEntity);
                        }));

        // Retourner la réponse DTO
        return SeriePostDtoResponseMapper.convertToSerieDto(serie);
    }

    private void handleGenres(SerieEntity serie, List<GenreDto> genreDtos) {
        List<String> genreNames = genreDtos.stream().map(GenreDto::name).toList();
        List<GenreEntity> existingGenres = genreRepository.findAllByName(genreNames);

        for (GenreDto genreDto : genreDtos) {
            GenreEntity genre = existingGenres.stream()
                    .filter(g -> g.getName().equals(genreDto.name()))
                    .findFirst()
                    .orElseGet(() -> genreRepository.save(new GenreEntity(null, genreDto.id(), genreDto.name())));
            if (!serie.getGenres().contains(genre)) {
                serie.getGenres().add(genre);
            }
        }
    }


    @Override
    public boolean updateFavorite(Long episodeId, Integer favorite, Long userId) throws SerieException {
        Optional<PlayListEpisodeEntity> playListTvEntity = this.playListTvRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isEmpty()) {
            throw new SerieException("Épisode introuvable", HttpStatus.NOT_FOUND);
        }

        playListTvEntity.get().setFavorite(favorite == 1);

        PlayListEpisodeEntity s = this.playListTvRepository.save(playListTvEntity.get());
        return s.isFavorite();

    }

    public void updateStatus(Long episodeId, StatusEnum status, Long userId) throws SerieException {
        Optional<PlayListEpisodeEntity> playListTvEntity = this.playListTvRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isPresent()) {
            playListTvEntity.get().setStatus(status);
            this.playListTvRepository.save(playListTvEntity.get());
            return;
        }
        throw new SerieException("Série introuvable", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<SerieGetResponseDto> findAllEpisodesByUser(UserEntity user) {
        List<EpisodeEntity> playlists = playListTvRepository.findEpisodesByUserId(user);

        List<EpisodeGetResponseDto> episodeDtos = playlists.stream()
                .map(episodeEntity -> {
                    Optional<PlayListEpisodeEntity> playListEpisodeEntity = playListTvRepository.findByEpisodeIdAndUserId(episodeEntity.getId(), user.getId());
                    return playListEpisodeEntity.map(listEpisodeEntity -> EpisodeGetResponseMapper.convertToDto(episodeEntity, listEpisodeEntity.isFavorite(), listEpisodeEntity.getStatus())).orElseGet(() -> EpisodeGetResponseMapper.convertToDto(episodeEntity, false, StatusEnum.A_REGARDER));
                })
                .toList();

        Comparator<EpisodeEntity> comparator = Comparator.comparing(EpisodeEntity::getCreatedDate).reversed();
        List<SerieEntity> serieEntities = playlists.stream()
                .sorted(comparator)
                .map(episodeEntity -> episodeEntity.getSeason().getSerie())
                .distinct() // pour éliminer les doublons
                .toList();

        return serieEntities.stream()
                .map(serieEntity -> {
                    SerieGetResponseDto serieDto = SerieGetResponseDtoMapper.convertToSerieDto(serieEntity);
                    List<SeasonGetResponseDto> seasonDtos = serieDto.seasons();
                    for (SeasonGetResponseDto seasonDto : seasonDtos) {
                        List<EpisodeGetResponseDto> seasonEpisodes = episodeDtos.stream()
                                .filter(episodeDto -> Objects.equals(episodeDto.getSeasonNumber(), seasonDto.getSeasonNumber()))
                                .toList();
                        seasonDto.setEpisodes(seasonEpisodes);
                    }
                    return serieDto;
                })
                .toList();
    }


}
