package org.lafabrique_epita.application.service.media.serie;

import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDtoMapper;
import org.lafabrique_epita.domain.entities.*;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.EpisodeException;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
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
    private final PlayListTvRepository playListTvRepository;

    public EpisodeServiceAdapter(EpisodeRepository episodeRepository, SeasonRepository seasonRepository, PlayListTvRepository playListTvRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
        this.playListTvRepository = playListTvRepository;
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
}
