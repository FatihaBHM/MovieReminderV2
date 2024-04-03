package org.lafabrique_epita.application.service.media.serie;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDtoMapper;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.lafabrique_epita.domain.repositories.PlayListEpisodeRepository;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class SerieServiceAdapter implements SerieServicePort {

    private final SerieRepository serieRepository;
    private final PlayListEpisodeRepository playListEpisodeRepository;
    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;

    public SerieServiceAdapter(SerieRepository serieRepository, PlayListEpisodeRepository playListEpisodeRepository, EpisodeRepository episodeRepository, SeasonRepository seasonRepository) {
        this.serieRepository = serieRepository;
        this.playListEpisodeRepository = playListEpisodeRepository;
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    public SerieEntity save(SerieEntity serie) {
        return serieRepository.save(serie);
    }

    @Override
    public SerieGetResponseDto findSerieByIdTmdb(Long idTmdb) {
        return this.serieRepository.findByIdTmdb(idTmdb)
                .map(SerieGetResponseDtoMapper::convertToSerieDto)
                .orElse(null);
    }

    @Override
    public void delete(Long serieId, Long userId) throws SerieException {
        // Trouvez la série par son ID
        SerieEntity serie = findSerieById(serieId);
        boolean isUsedByOtherUsers = false;

        // Pour chaque saison de la série
        for (SeasonEntity season : serie.getSeasons()) {
            // Pour chaque épisode de la saison
            for (EpisodeEntity episode : season.getEpisodes()) {
                isUsedByOtherUsers = handleEpisodeDeletion(episode, userId, isUsedByOtherUsers);
            }
            if (!isUsedByOtherUsers) {
                seasonRepository.delete(season);
            }
        }

        if (!isUsedByOtherUsers) {
            serieRepository.delete(serie);
        } else {
            log.error("La série | id: {} - id_tmdb: {} - title: {} | est utilisée par quelqu'un d'autre", serie.getId(), serie.getIdTmdb(), serie.getTitle());
            throw new SerieException("La série est supprimée de votre playlist", HttpStatus.CONFLICT);
        }
    }

    private SerieEntity findSerieById(Long serieId) throws SerieException {
        return serieRepository.findById(serieId)
                .orElseThrow(() -> new SerieException("Série introuvable", HttpStatus.NOT_FOUND));
    }

    private boolean handleEpisodeDeletion(EpisodeEntity episode, Long userId, boolean isUsedByOtherUsers) {
        // Supprimer les épisodes de la playlist de l'utilisateur
        Optional<PlayListEpisodeEntity> byEpisodeIdAndUserId = playListEpisodeRepository.findByEpisodeIdAndUserId(episode.getId(), userId);
        byEpisodeIdAndUserId.ifPresent(playListEpisodeRepository::delete);

        // Supprimer les épisodes si aucun autre utilisateur ne les utilise
        List<PlayListEpisodeEntity> otherUsersPlaylists = playListEpisodeRepository.findByEpisodeAndUserIdNot(episode, userId);
        if (otherUsersPlaylists.isEmpty()) {
            episodeRepository.delete(episode);
        } else {
            isUsedByOtherUsers = true;
        }
        return isUsedByOtherUsers;
    }

}
