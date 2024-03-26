package org.lafabrique_epita.infrastructure.user;


import org.lafabrique_epita.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepositoryPort extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);
}
