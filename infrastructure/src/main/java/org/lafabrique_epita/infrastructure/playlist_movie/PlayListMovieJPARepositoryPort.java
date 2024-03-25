package org.lafabrique_epita.infrastructure.playlist_movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PlayListMovieJPARepositoryPort extends JpaRepository<PlayListMovieEntity, Long> {
    Optional<PlayListMovieEntity> findByMovieIdAndUserId(Long movieId, Long userId);

    PlayListMovieEntity findByUserIdAndFavoriteTrue(Long userId);

    @Query("SELECT movie FROM PlayListMovieEntity WHERE user = :user")
    List<MovieEntity> findMoviesByUserId(@PathVariable UserEntity user);

    boolean existsByMovieIdAndUserId(Long movieId, Long userId);
}
