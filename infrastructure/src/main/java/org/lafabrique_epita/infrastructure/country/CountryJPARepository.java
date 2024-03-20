package org.lafabrique_epita.infrastructure.country;

import org.lafabrique_epita.domain.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryJPARepository extends JpaRepository<CountryEntity, Long> {
}
