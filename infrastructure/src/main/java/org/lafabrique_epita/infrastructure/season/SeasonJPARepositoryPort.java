package org.lafabrique_epita.infrastructure.season;

import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonJPARepositoryPort extends JpaRepository<SeasonEntity, Long> {
    Optional<SeasonEntity> findByIdTmdb(Long idTmdbSeason);
}
