package org.lafabrique_epita.application.service.media.movie;


import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieServiceAdapter implements MovieServicePort {
    private final MovieRepository movieRepository;

    public MovieServiceAdapter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieEntity save(MovieEntity movie) {
        return movieRepository.save(movie);
    }
}
