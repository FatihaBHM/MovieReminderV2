package org.lafabrique_epita.infrastructure.favorite;

import org.lafabrique_epita.domain.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteJPARepository extends JpaRepository<FavoriteEntity, Long> {
}
