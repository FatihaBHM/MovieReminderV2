package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PlayListEpisodeRepository {

    PlayListEpisodeEntity save(PlayListEpisodeEntity playListEpisodeEntity);

    void delete(PlayListEpisodeEntity playListEpisodeEntity);

    Optional<PlayListEpisodeEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    List<EpisodeEntity> findEpisodesByUserId(UserEntity user);

    List<PlayListEpisodeEntity> findByEpisodeAndUserIdNot(EpisodeEntity episode, Long userId);
}
