package org.lafabrique_epita.application.service.media.playlist_series;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDtoMapper;
import org.lafabrique_epita.application.dto.media.serie_post.*;
import org.lafabrique_epita.domain.entities.*;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.EpisodeException;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.GenreRepository;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PlaylistTvServiceAdapter implements PlaylistTvServicePort {
    private final PlayListTvRepository playListTvRepository;
    private final SerieRepository serieRepository;
    private final GenreRepository genreRepository;
    private final SeasonRepository seasonRepository;

    public PlaylistTvServiceAdapter(PlayListTvRepository playListTvRepository, SerieRepository serieRepository, GenreRepository genreRepository, SeasonRepository seasonRepository) {
        this.playListTvRepository = playListTvRepository;
        this.serieRepository = serieRepository;
        this.genreRepository = genreRepository;
        this.seasonRepository = seasonRepository;
    }


    @Override
    public SeriePostResponseDto save(SeriePostDto seriePostDto, UserEntity user) throws SerieException {
        // vérifier si l'utilisateur n'a pas déjà ajouté cette série à sa liste
        Optional<SerieEntity> serieEntity = this.serieRepository.findByIdTmdb(seriePostDto.idTmdb());
        SerieEntity serie;
        if (serieEntity.isPresent()) {
            serie = serieEntity.get();
            boolean existsBySerieIdAndUserId = this.playListTvRepository.existsByEpisodeIdAndUserId(serie.getId(), user.getId());
            if (existsBySerieIdAndUserId) {
                throw new SerieException("La série existe déjà dans la playlist", HttpStatus.CONFLICT);
            }
        } else {
            serie = SeriePostDtoMapper.convertToSerieEntity(seriePostDto);

            // vérifier si les genres existent déjà en base par leur 'name' et dans ce cas save le genre
            // qui n'existe pas puis récupérer tous les genres pour les envoyer dans serie
            List<String> genres = seriePostDto.genres().stream().map(GenreDto::name).toList();
            List<GenreEntity> genreEntities = new ArrayList<>(genreRepository.findAllByName(genres));
            for (GenreEntity genre : serie.getGenres()) {
                if (genreEntities.stream().noneMatch(genreEntity -> genreEntity.getName().equals(genre.getName()))) {
                    genreEntities.add(genreRepository.save(genre));
                }
            }
            serie.setGenres(genreEntities);

            log.error("Série: {}", serie);
            serie = this.serieRepository.save(serie);
        }

        createAndSavePlayListTvEntity(serie);
        return SeriePostDtoResponseMapper.convertToSerieDto(serie);
    }

    @Override
    public EpisodePostDto save(EpisodeEntity episodeEntity, UserEntity user) throws EpisodeException {
        PlayListEpisodeEntity playListEpisodeEntity = new PlayListEpisodeEntity();
        playListEpisodeEntity.setEpisode(episodeEntity);
        playListEpisodeEntity.setUser(user);
        playListEpisodeEntity.setFavorite(false);
        playListEpisodeEntity.setStatus(StatusEnum.A_REGARDER);
        playListEpisodeEntity.setScore(0);

        PlayListEpisodeID playListEpisodeID = new PlayListEpisodeID();
        playListEpisodeID.setEpisodeId(episodeEntity.getId());
        playListEpisodeID.setUserId(user.getId());

        playListEpisodeEntity.setId(playListEpisodeID);

        PlayListEpisodeEntity pl = this.playListTvRepository.save(playListEpisodeEntity);
        return EpisodePostDtoMapper.convertToDto(pl.getEpisode());
    }

    @SneakyThrows
    private void createAndSavePlayListTvEntity(SerieEntity serie) {
        try {
            List<SeasonEntity> seasons = serie.getSeasons();

            for (SeasonEntity season : seasons) {
                season.setSerie(serie);
                this.seasonRepository.save(season);
            }

        } catch (Exception e) {
            throw new SerieException("Un problème est survenu lors de l'enregistrement des épisodes dans la playlist TV", HttpStatus.CONFLICT);
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

        Comparator<EpisodeEntity> comparator = Comparator.comparing(EpisodeEntity::getCreatedDate).reversed();
        // transformer la liste d'EpisodeEntity en liste de SerieEntity
        // puis transformer la liste de SerieEntity en liste de SerieGetResponseDto
        List<SerieEntity> serieEntities = playlists.stream()
                .sorted(comparator)
                .map(episodeEntity -> episodeEntity.getSeason().getSerie())
                .distinct() // pour éliminer les doublons
                .toList();

        return serieEntities.stream()
                .map(SerieGetResponseDtoMapper::convertToSerieDto)
                .toList();
    }


}
