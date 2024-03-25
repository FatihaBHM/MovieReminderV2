package org.lafabrique_epita.infrastructure.episode;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeJPARepositoryPort extends JpaRepository<EpisodeEntity, Long> {
}
