package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PlayListTvRepository {

    PlayListEpisodeEntity save(PlayListEpisodeEntity playListEpisodeEntity);

    void delete(PlayListEpisodeEntity playListEpisodeEntity);

    Optional<PlayListEpisodeEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    List<EpisodeEntity> findEpisodesByUserId(UserEntity user);

    boolean existsByEpisodeIdAndUserId(Long episodeId, Long userId);

}
