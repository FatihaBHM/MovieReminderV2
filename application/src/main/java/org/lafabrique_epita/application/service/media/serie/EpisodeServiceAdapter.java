package org.lafabrique_epita.application.service.media.serie;

import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDtoMapper;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EpisodeServiceAdapter implements EpisodeServicePort {


    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;

    public EpisodeServiceAdapter(EpisodeRepository episodeRepository, SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public List<EpisodeEntity> saveAll(List<EpisodePostDto> episodePostDtos, Long idTmdbSeason) throws SerieException {
        // on récupère une DTO et on la convertit en entité
        List<EpisodeEntity> episodes = episodePostDtos.stream().map(EpisodePostDtoMapper::convertToEntity).toList();

        List<EpisodeEntity> finalEpisodes = new ArrayList<>();
        // On doit vérifier si l'épisode existe déjà dans la base de données
        for (EpisodeEntity episode : episodes) {
            Optional<EpisodeEntity> episodeEntity = this.episodeRepository.findByIdTmdb(episode.getIdTmdb());
            // Si oui, on retourne l'épisode
            if (episodeEntity.isPresent()) {
                finalEpisodes.add(episodeEntity.get());
                continue;
            }

            // Sinon, on crée un nouvel épisode et on le retourne
            Optional<SeasonEntity> seasonEntity = this.seasonRepository.findByIdTmdb(idTmdbSeason);
            if (seasonEntity.isEmpty()) {
                throw new SerieException("Saison introuvable");
            }
            episode.setSeason(seasonEntity.get());
            finalEpisodes.add(episode);
        }

        return this.episodeRepository.saveAll(finalEpisodes);
    }
}
