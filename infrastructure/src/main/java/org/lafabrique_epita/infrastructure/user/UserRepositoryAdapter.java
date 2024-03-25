package org.lafabrique_epita.infrastructure.user;

import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final IUserJpaRepositoryPort userJpaRepository;

    public UserRepositoryAdapter(IUserJpaRepositoryPort userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Long save(UserEntity user) {
        return userJpaRepository.save(user).getId();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userJpaRepository.findById(id);
    }

}
