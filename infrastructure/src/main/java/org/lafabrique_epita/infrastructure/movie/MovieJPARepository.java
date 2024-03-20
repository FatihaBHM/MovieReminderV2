package org.lafabrique_epita.infrastructure.movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJPARepository extends JpaRepository<MovieEntity, Long> {
}
