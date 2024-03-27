package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PlayListTvRepository {

    PlayListTvEntity save(PlayListTvEntity playListTvEntity);

//    boolean existsBySerieIdAndUserId(Long id, Long id1);

    void delete(PlayListTvEntity playListTvEntity);

    PlayListTvEntity findByUserIdAndFavoriteTrue(Long userId);

    Optional<PlayListTvEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    List<SerieEntity> findEpisodesByUserId(UserEntity user);

    boolean existsByEpisodeIdAndUserId(Long episodeId, Long userId);

}
