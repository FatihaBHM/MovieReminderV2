package org.lafabrique_epita.exposition.api.media;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.movie_post.MoviePostDto;
import org.lafabrique_epita.application.dto.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.application.service.media.MovieServiceAdapter;
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceAdapter;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.MovieException;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movie", description = "The movie API")
@RestController
//@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceAdapter movieService;

    private final PlaylistMovieServiceAdapter playlistMovieService;

    private final ObjectMapper objectMapper;


    public MovieController(MovieServiceAdapter movieService, PlaylistMovieServiceAdapter playlistMovieService, ObjectMapper objectMapper) {
        this.movieService = movieService;
        this.playlistMovieService = playlistMovieService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Add a movie to the playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to the playlist"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"status\": 400, \"errorMessage\": {\"title\": \"ne doit pas être vide\"} }"),
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"status\": 409, \"errorMessage\": \"Movie already exists in the playlist\" }"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PostMapping("/movies")
    public ResponseEntity<MoviePostResponseDto> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication) throws MovieException, JsonProcessingException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        MoviePostResponseDto movieDTO = playlistMovieService.save(moviePostDto, userEntity);

        return ResponseEntity.ok(movieDTO);
    }

    // /movies/{id}?favorite=1
    @Operation(summary = "Add or remove a movie from the favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to the favorite list"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Favorite must be 0 or 1(0 => remove, 1 => add)\",\"status\":400}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
    })
    @GetMapping("/movies/{id}")
    public ResponseEntity<Favorite> getFrontMovie(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer favorite,
            Authentication authentication
    ) throws MovieException {
        if (favorite < 0 || favorite > 1) {
            throw new MovieException("Favorite must be 0 or 1(0 => remove, 1 => add)", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        boolean fav = playlistMovieService.setFavorite(id, favorite, userEntity.getId());

        Favorite favoriteResponse = new Favorite(fav);

        return ResponseEntity.ok(favoriteResponse);
    }

    public record Favorite(boolean favorite) {}

    @GetMapping("/movies")
    public ResponseEntity<List<MovieGetResponseDTO>> getAllMoviesByUser(Authentication authentication) {

        UserEntity userEntity =  (UserEntity) authentication.getPrincipal();

        //Récupérer la liste de films du user dans le service
        List<MovieGetResponseDTO> playListMovies = playlistMovieService.findAllMoviesByUser(userEntity);

         return ResponseEntity.ok(playListMovies);
    }
}
