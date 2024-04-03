package org.lafabrique_epita.application.service.media.playlist_series;

import jakarta.transaction.Transactional;
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

@Slf4j
@Service
@Transactional
public class PlaylistEpisodeServiceAdapter implements PlaylistEpisodeServicePort {
    private final PlayListEpisodeRepository playListEpisodeRepository;
    private final SerieRepository serieRepository;
    private final GenreRepository genreRepository;
    private final SeasonRepository seasonRepository;

    public PlaylistEpisodeServiceAdapter(PlayListEpisodeRepository playListEpisodeRepository, SerieRepository serieRepository, GenreRepository genreRepository, SeasonRepository seasonRepository) {
        this.playListEpisodeRepository = playListEpisodeRepository;
        this.serieRepository = serieRepository;
        this.genreRepository = genreRepository;
        this.seasonRepository = seasonRepository;
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
            // Enregistrer la série
            serieRepository.save(serie);
        }

        List<SeasonEntity> seasons = new ArrayList<>();
        for (SeasonPostDto seasonDto : seriePostDto.seasons()) {
            SeasonEntity seasonEntity = serie.getSeasons().stream()
                    .filter(season -> season.getIdTmdb().equals(seasonDto.idTmdb()))
                    .findFirst()
                    .orElse(new SeasonEntity()); // Crée une nouvelle SeasonEntity si aucune correspondance n'est trouvée

            seasonEntity.setIdTmdb(seasonDto.idTmdb());
            seasonEntity.setOverview(seasonDto.overview());
            seasonEntity.setPosterPath(seasonDto.posterPath());
            seasonEntity.setSeasonNumber(seasonDto.seasonNumber());
            seasonEntity.setSerie(serie);
            this.seasonRepository.save(seasonEntity);
            seasons.add(seasonEntity);
        }

        serie.setSeasons(seasons);
        serieRepository.save(serie);

        // Retourner la réponse DTO
        return SeriePostDtoResponseMapper.convertToSerieDto(serie);
    }

    private void handleGenres(SerieEntity serie, List<GenreDto> genreDtos) {
        List<String> genreNames = genreDtos.stream().map(GenreDto::name).toList();
        List<GenreEntity> existingGenres = genreRepository.findAllByName(genreNames);
        // Ajouter les genres manquants dans la base de données avec le nom est idTmdb
        List<GenreEntity> genres = new ArrayList<>();
        for (GenreDto genreDto : genreDtos) {
            if (existingGenres.stream().noneMatch(genre -> genre.getName().equalsIgnoreCase(genreDto.name()))) {
                GenreEntity genre = new GenreEntity();
                genre.setName(genreDto.name());
                genre.setIdTmdb(genreDto.id());
                genreRepository.save(genre);
                genres.add(genre);
            } else {
                GenreEntity genre = existingGenres.stream()
                        .filter(g -> g.getName().equalsIgnoreCase(genreDto.name()))
                        .findFirst()
                        .orElseThrow();
                genres.add(genre);
            }
        }
        serie.setGenres(genres);
    }

    @Override
    public boolean updateFavorite(Long episodeId, Integer favorite, Long userId) throws SerieException {
        Optional<PlayListEpisodeEntity> playListTvEntity = this.playListEpisodeRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isEmpty()) {
            throw new SerieException("Épisode introuvable", HttpStatus.NOT_FOUND);
        }

        playListTvEntity.get().setFavorite(favorite == 1);

        PlayListEpisodeEntity s = this.playListEpisodeRepository.save(playListTvEntity.get());
        return s.isFavorite();

    }

    public void updateStatus(Long episodeId, StatusEnum status, Long userId) throws SerieException {
        Optional<PlayListEpisodeEntity> playListTvEntity = this.playListEpisodeRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isPresent()) {
            playListTvEntity.get().setStatus(status);
            this.playListEpisodeRepository.save(playListTvEntity.get());
            return;
        }
        throw new SerieException("Série introuvable", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<SerieGetResponseDto> findAllEpisodesByUser(UserEntity user) {
        List<EpisodeEntity> playlists = playListEpisodeRepository.findEpisodesByUserId(user);

        List<EpisodeGetResponseDto> episodeDtos = playlists.stream()
                .map(episodeEntity -> {
                    Optional<PlayListEpisodeEntity> playListEpisodeEntity = playListEpisodeRepository.findByEpisodeIdAndUserId(episodeEntity.getId(), user.getId());
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
