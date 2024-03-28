package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListTvEntity;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PlayListTvRepository {

    PlayListTvEntity save(PlayListTvEntity playListTvEntity);

    void delete(PlayListTvEntity playListTvEntity);

    Optional<PlayListTvEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    List<EpisodeEntity> findEpisodesByUserId(UserEntity user);

    boolean existsByEpisodeIdAndUserId(Long episodeId, Long userId);

}
