package org.lafabrique_epita.infrastructure.genre;

import org.lafabrique_epita.domain.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreJPARepository extends JpaRepository<GenreEntity, Long> {
}
