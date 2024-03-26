package org.lafabrique_epita.infrastructure.movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieJPARepositoryPort extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByIdTmdb(Long idTmdb);
}
