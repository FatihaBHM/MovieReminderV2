package org.lafabrique_epita.application.service.media.playlist_series;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDtoMapper;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDtoMapper;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDtoResponseMapper;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostResponseDto;
import org.lafabrique_epita.domain.entities.*;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaylistTvServiceAdapter implements PlaylistTvServicePort {
    private final PlayListTvRepository playListTvRepository;
    private final SerieRepository serieRepository;


    public PlaylistTvServiceAdapter(PlayListTvRepository playListTvRepository, SerieRepository serieRepository) {
        this.playListTvRepository = playListTvRepository;
        this.serieRepository = serieRepository;
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
                throw new SerieException("Serie already exists in the playlist", HttpStatus.CONFLICT);
            }
        } else {
            serie = SeriePostDtoMapper.convertToSerieEntity(seriePostDto);
            serie = this.serieRepository.save(serie);
        }

        createAndSavePlayListTvEntity(serie, user);
        return SeriePostDtoResponseMapper.convertToSerieDto(serie);
    }

        @SneakyThrows
        private void createAndSavePlayListTvEntity(SerieEntity serie, UserEntity user) {
        try{
                List<SeasonEntity> seasons = serie.getSeasons();
                for (SeasonEntity season : seasons){
                    List<EpisodeEntity> episodes = season.getEpisodes();
                    for (EpisodeEntity episode : episodes) {
                        PlayListTvID playListTvID = new PlayListTvID(episode.getId(), user.getId());
                        PlayListTvEntity playListTv = new PlayListTvEntity();
                        playListTv.setEpisode(episode);
                        playListTv.setId(playListTvID);
                        playListTv.setUser(user);
                        playListTv.setStatus(StatusEnum.A_REGARDER);
                        this.playListTvRepository.save(playListTv);
                    }
                }
            }catch (Exception e) {
                throw new SerieException("Un problème est survenu lors de l'enregistrement des épisodes dans la playlist TV", HttpStatus.CONFLICT);
            }

        }

//    @Override
//    public SeriePostResponseDto findByUserAndBySerie(Long serieId, Long userId) {
//        PlayListTvID playListTvID = new PlayListTvID(serieId, userId);
//        Optional<PlayListTvEntity> playListTvEntity = this.playListTvRepository.findBySerieIdAndUserId(playListTvID);
//        return playListTvEntity.map(listSerieEntity -> SeriePostDtoResponseMapper.convertToSerieDto(listSerieEntity.getSerie())).orElse(null);
//    }

    @Override
    public boolean updateFavorite(Long episodeId, Integer favorite, Long userId) throws SerieException {
        Optional<PlayListTvEntity> playListTvEntity = this.playListTvRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isEmpty()) {
            throw new SerieException("Episode not found", HttpStatus.NOT_FOUND);
        }

        playListTvEntity.get().setFavorite(favorite == 1);

        PlayListTvEntity s = this.playListTvRepository.save(playListTvEntity.get());
        return s.isFavorite();

    }

    public void updateStatus(Long episodeId, StatusEnum status, Long userId) throws SerieException {
        Optional<PlayListTvEntity> playListTvEntity = this.playListTvRepository.findByEpisodeIdAndUserId(episodeId, userId);

        if (playListTvEntity.isPresent()) {
            playListTvEntity.get().setStatus(status);
            this.playListTvRepository.save(playListTvEntity.get());
            return;
        }
        throw new SerieException("Serie not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long episodeId, int i, Long userId) throws SerieException {
        Optional<PlayListTvEntity> playListTvEntity = this.playListTvRepository.findByEpisodeIdAndUserId(episodeId, userId);
        if (playListTvEntity.isPresent()) {
            this.playListTvRepository.delete(playListTvEntity.get());
        } else {
            throw new SerieException("Serie not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public SerieGetResponseDto findSerieByIdTmdb(Long idTmdb) throws SerieException {
        Optional<SerieEntity> serieEntity = this.serieRepository.findByIdTmdb(idTmdb);
        if (serieEntity.isPresent()) {
                return SerieGetResponseDtoMapper.convertToSerieDto(serieEntity.get());
        }
        throw new SerieException("Serie not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<SerieGetResponseDto> findAllEpisodesByUser(UserEntity user) {
        List<SerieEntity> playlists = playListTvRepository.findEpisodesByUserId(user);

        return playlists.stream()
                .map(SerieGetResponseDtoMapper::convertToSerieDto).toList();
    }



}
