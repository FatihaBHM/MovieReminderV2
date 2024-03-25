package org.lafabrique_epita.infrastructure.movie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryAdapter implements MovieRepository {

    private final MovieJPARepositoryPort movieJPARepository;

    public MovieRepositoryAdapter(MovieJPARepositoryPort movieJPARepository) {
        this.movieJPARepository = movieJPARepository;
    }

    @Override
    public MovieEntity save(MovieEntity movie) {
        // save comment
        // save genre
        return movieJPARepository.save(movie);
    }

    @Override
    public List<MovieEntity> findAll() {
        return movieJPARepository.findAll();
    }

    @Override
    public Optional<MovieEntity> findById(Long id) {
        return movieJPARepository.findById(id);
    }

    @Override
    public Optional<MovieEntity> findByIdTmdb(Long idTmdb) {
        return movieJPARepository.findByIdTmdb(idTmdb);
    }
}
