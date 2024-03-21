package org.lafabrique_epita.exposition.api.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.service.media.MovieServiceImpl;
import org.lafabrique_epita.domain.entities.MovieEntity;

import org.lafabrique_epita.exposition.dto.movie.MoviePostDto;
import org.lafabrique_epita.exposition.dto.movie.MoviePostDtoMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceImpl movieService;
    private final ObjectMapper mapper;


    public MovieController(MovieServiceImpl movieService, ObjectMapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;

    }

    @PostMapping("/movies")
    public ResponseEntity<MovieEntity> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto) {
        MovieEntity movie = MoviePostDtoMapper.convertToMovieEntity(moviePostDto);
       MovieEntity movieEntity = movieService.save(movie);
        return ResponseEntity.ok(movieEntity);
    }


}
