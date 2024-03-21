package org.lafabrique_epita.exposition.api.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.service.media.MovieServiceImpl;
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceImpl;
import org.lafabrique_epita.domain.entities.MovieEntity;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.exposition.dto.moviePost.MoviePostDto;
import org.lafabrique_epita.exposition.dto.moviePost.MoviePostDtoMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceImpl movieService;

    private final PlaylistMovieServiceImpl playlistMovieService;
    private final ObjectMapper mapper;


    public MovieController(MovieServiceImpl movieService, PlaylistMovieServiceImpl playlistMovieService, ObjectMapper mapper) {
        this.movieService = movieService;
        this.playlistMovieService = playlistMovieService;
        this.mapper = mapper;

    }

    @PostMapping("/movies")
    public ResponseEntity<MovieEntity> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        System.out.println(userEntity);

        MovieEntity movie = MoviePostDtoMapper.convertToMovieEntity(moviePostDto);
        MovieEntity movieEntity = movieService.save(movie);

        PlayListMovieID playListMovieID = new PlayListMovieID(movie.getId(), userEntity.getId());

        PlayListMovieEntity playListMovieEntity = new PlayListMovieEntity();
        playListMovieEntity.setId(playListMovieID);
        playListMovieEntity.setMovie(movie);
        playListMovieEntity.setUser(userEntity);
        playListMovieEntity.setStatus(StatusEnum.A_REGARDER);
        playlistMovieService.save(playListMovieEntity);

        return ResponseEntity.ok(movieEntity);
    }



}
