package org.lafabrique_epita.application.service.media.serie;

import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDtoMapper;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EpisodeServiceAdapter implements EpisodeServicePort {


    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;

    public EpisodeServiceAdapter(EpisodeRepository episodeRepository, SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public EpisodeEntity save(EpisodePostDto episodePostDto, Long idTmdbSeason) throws SerieException {
        // on récupère une DTO et on la convertit en entité
        EpisodeEntity episode = EpisodePostDtoMapper.convertToEntity(episodePostDto);

        // On doit vérifier si l'épisode existe déjà dans la base de données
        Optional<EpisodeEntity> episodeEntity = this.episodeRepository.findByIdTmdb(episode.getIdTmdb());

        // Si oui, on retourne l'épisode
        if (episodeEntity.isPresent()) {
           return episodeEntity.get();
        }

        // Sinon, on crée un nouvel épisode et on le retourne
        Optional<SeasonEntity> seasonEntity = this.seasonRepository.findByIdTmdb(idTmdbSeason);
        if (seasonEntity.isEmpty()) {
            throw new SerieException("Saison introuvable");
        }
        episode.setSeason(seasonEntity.get());
        return this.episodeRepository.save(episode);
    }
}
