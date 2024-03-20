package org.lafabrique_epita.infrastructure.movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryAdapter implements MovieRepository {

        private final MovieJPARepository movieJPARepository;

        public MovieRepositoryAdapter(MovieJPARepository movieJPARepository) {
            this.movieJPARepository = movieJPARepository;
        }

    @Override
    public MovieEntity save(MovieEntity movie) {
        return movieJPARepository.save(movie);
    }

    @Override
    public List<MovieEntity> findAll() {
        return movieJPARepository.findAll();
    }
}
