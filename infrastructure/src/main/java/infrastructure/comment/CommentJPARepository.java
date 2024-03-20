package infrastructure.comment;

import org.lafabrique_epita.domain.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJPARepository extends JpaRepository<CommentEntity, Long> {
}
