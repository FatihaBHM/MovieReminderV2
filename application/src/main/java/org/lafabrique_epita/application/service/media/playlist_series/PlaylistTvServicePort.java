package org.lafabrique_epita.application.service.media.playlist_series;

import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostResponseDto;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.SerieException;

import java.util.List;

public interface PlaylistTvServicePort {

    SeriePostResponseDto save(SeriePostDto seriePostDto, UserEntity user) throws SerieException;
    List<SerieGetResponseDto> findAllEpisodesByUser(UserEntity user);

    boolean updateFavorite(Long episodeId, Integer favorite, Long userId) throws SerieException;

    void updateStatus(Long episodeId, StatusEnum status, Long userId) throws SerieException;

}
