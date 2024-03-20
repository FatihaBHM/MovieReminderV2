package infrastructure.season;

import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonJPARepository extends JpaRepository<SeasonEntity, Long> {
}
