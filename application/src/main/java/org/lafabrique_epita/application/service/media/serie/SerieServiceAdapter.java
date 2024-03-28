package org.lafabrique_epita.application.service.media.serie;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDtoMapper;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SerieServiceAdapter implements SerieServicePort {

    private final SerieRepository serieRepository;
    private final PlayListTvRepository playlistTvService;

    public SerieServiceAdapter(SerieRepository serieRepository, PlayListTvRepository playlistTvService) {
        this.serieRepository = serieRepository;
        this.playlistTvService = playlistTvService;
    }

    public SerieEntity save(SerieEntity serie) {
        return serieRepository.save(serie);
    }

    public List<SerieEntity> getAll() {
        return serieRepository.findAll();
    }

    @Override
    public SerieGetResponseDto findSerieByIdTmdb(Long idTmdb) {
        return this.serieRepository.findByIdTmdb(idTmdb)
                .map(SerieGetResponseDtoMapper::convertToSerieDto)
                .orElse(null);
    }

    @Override
    public void delete(Long serieId, Long userId) throws SerieException {
        SerieEntity serie = this.serieRepository.findById(serieId)
                .orElseThrow(() -> new SerieException("Série introuvable"));

        try {
            List<EpisodeEntity> episodes = serie.getSeasons().stream()
                    .flatMap(season -> season.getEpisodes().stream()).toList();

            episodes.stream()
                    .map(episode -> playlistTvService.findByEpisodeIdAndUserId(episode.getId(), userId))
                    .forEach(playlistTvEntity -> playlistTvEntity.ifPresent(playlistTvService::delete));
        } catch (Exception e) {
            log.error("Erreur lors de la suppression d'épisodes de la playlist", e);
            throw new SerieException("Erreur lors de la suppression d'épisodes de la playlist");
        }

        try {
            serieRepository.delete(serie);
        } catch (Exception e) {
            log.error("La série est utilisée par quelqu'un d'autre", e);
            throw new SerieException("La série est supprimé de votre playlist");
        }
    }

}
