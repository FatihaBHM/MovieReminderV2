package org.lafabrique_epita.infrastructure.language;

import org.lafabrique_epita.domain.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageJPARepository extends JpaRepository<LanguageEntity, Long> {
}
