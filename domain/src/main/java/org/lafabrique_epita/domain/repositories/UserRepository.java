package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);

    Long save(UserEntity user);

    Optional<UserEntity> findById(Long id);
}
