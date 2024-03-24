package org.lafabrique_epita.exposition.api.media;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.service.media.MovieServiceAdapter;
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceAdapter;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.PlayListMovieException;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDto;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDtoMapper;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDtoResponseMapper;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Movie", description = "The movie API")
@RestController
public class MovieController {

    private final MovieServiceAdapter movieService;

    private final PlaylistMovieServiceAdapter playlistMovieService;


    public MovieController(MovieServiceAdapter movieService, PlaylistMovieServiceAdapter playlistMovieService) {
        this.movieService = movieService;
        this.playlistMovieService = playlistMovieService;
    }

    @Operation(summary = "Add a movie to the playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to the playlist"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
    })
    @PostMapping("/movies")
    public ResponseEntity<MoviePostResponseDto> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication)
            throws PlayListMovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

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

    // /movies/{id}?favorite=1
    @Operation(summary = "Add or remove a movie from the favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to the favorite list"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
    })
    @GetMapping("/movies/{id}")
    public ResponseEntity<Favorite> getFrontMovie(
            @PathVariable Long id,
            @RequestParam Integer favorite,
            Authentication authentication
    )
            throws PlayListMovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        PlayListMovieID playListMovieID = new PlayListMovieID(id, userEntity.getId());

        PlayListMovieEntity playListMovieEntity = playlistMovieService.findByUserAndByMovie(playListMovieID);

        playListMovieEntity.setFavorite(favorite == 1);

        playlistMovieService.save(playListMovieEntity);

        Favorite favoriteResponse = new Favorite(playListMovieEntity.isFavorite());

        return ResponseEntity.ok(favoriteResponse);
    }

    public record Favorite(boolean favorite) {
    }

}
