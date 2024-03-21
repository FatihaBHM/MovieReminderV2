package org.lafabrique_epita.exposition.api.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.lafabrique_epita.exposition.dto.moviePost.MoviePostDtoResponseMapper;
import org.lafabrique_epita.exposition.dto.moviePost.MoviePostResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(
            summary = "Get a movie",
            description = "Get a movie by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie found", content = @Content(mediaType = "application/json", schema =@Schema(implementation = MovieEntity.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieEntity.class)))
            }
    )
    @PostMapping("/movies")
    public ResponseEntity<MoviePostResponseDto> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication) {
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

        return ResponseEntity.ok(MoviePostDtoResponseMapper.convertToMovieDto(movieEntity));
    }


}
