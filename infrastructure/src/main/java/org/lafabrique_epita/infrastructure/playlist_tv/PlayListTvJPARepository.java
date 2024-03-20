package org.lafabrique_epita.infrastructure.playlist_tv;

import org.lafabrique_epita.domain.entities.PlayListTvEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListTvJPARepository extends JpaRepository<PlayListTvEntity, Long> {
}
