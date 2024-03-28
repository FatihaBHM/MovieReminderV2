package org.lafabrique_epita.infrastructure.episode;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeJPARepositoryPort extends JpaRepository<EpisodeEntity, Long> {
    Optional<EpisodeEntity> findByIdTmdb(Long idTmdb);
}
