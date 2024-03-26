package org.lafabrique_epita.infrastructure.serie;


import org.lafabrique_epita.domain.entities.SerieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface SerieJPARepository extends JpaRepository<SerieEntity, Long> {

}
