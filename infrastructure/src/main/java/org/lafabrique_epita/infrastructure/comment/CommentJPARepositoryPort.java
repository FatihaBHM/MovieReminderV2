package org.lafabrique_epita.infrastructure.comment;

import org.lafabrique_epita.domain.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJPARepositoryPort extends JpaRepository<CommentEntity, Long> {
}
