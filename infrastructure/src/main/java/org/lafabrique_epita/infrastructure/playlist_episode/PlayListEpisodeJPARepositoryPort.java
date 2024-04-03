package org.lafabrique_epita.infrastructure.playlist_episode;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PlayListEpisodeJPARepositoryPort extends JpaRepository<PlayListEpisodeEntity, Long> {

    Optional<PlayListEpisodeEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    List<PlayListEpisodeEntity> findByEpisodeIdAndUserIdNot(Long episodeId, Long userId);

    @Query("SELECT episode FROM PlayListEpisodeEntity WHERE user = :user")
    List<EpisodeEntity> findEpisodesByUserId(@PathVariable UserEntity user);

}
