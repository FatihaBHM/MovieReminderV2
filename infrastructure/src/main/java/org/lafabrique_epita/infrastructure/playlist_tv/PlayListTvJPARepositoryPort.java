package org.lafabrique_epita.infrastructure.playlist_tv;

import org.lafabrique_epita.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PlayListTvJPARepositoryPort extends JpaRepository<PlayListTvEntity, Long> {

    Optional<PlayListTvEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    PlayListTvEntity findByUserIdAndFavoriteTrue(Long userId);

    @Query("SELECT episode FROM PlayListTvEntity WHERE user = :user")
    List<SerieEntity> findEpisodesByUserId(@PathVariable UserEntity user);

    boolean existsByEpisodeIdAndUserId(Long serieId, Long userId);


}
