package org.lafabrique_epita.application.service.media;

import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDtoMapper;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<MovieEntity> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public MovieGetResponseDTO findMovieByIdTmdb(Long idTmdb) {
        return this.movieRepository.findByIdTmdb(idTmdb)
                .map(MovieGetResponseDtoMapper::convertToMovieDto)
                .orElse(null);
    }
}
