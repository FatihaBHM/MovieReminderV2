package infrastructure.playlist_movie;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListMovieJpaRepository extends JpaRepository<PlayListMovieEntity, Long> {
}
