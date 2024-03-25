package org.lafabrique_epita.exposition.api.media;

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
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceAdapter;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
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

    private final PlaylistMovieServiceAdapter playlistMovieService;

    public MovieController(PlaylistMovieServiceAdapter playlistMovieService) {
        this.playlistMovieService = playlistMovieService;
    }

    @Operation(summary = "Ajouter un film à la playlist de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film ajouté à la playlist"),
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
    public ResponseEntity<MoviePostResponseDto> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication) throws MovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        MoviePostResponseDto movieDTO = playlistMovieService.save(moviePostDto, userEntity);

        return ResponseEntity.ok(movieDTO);
    }

    // /movies/{id}?favorite=1 (0 => remove, 1 => add)
    // /movies/{id}?status=0 (0	A_REGARDER - 1 EN_COURS - 2 VU - 3 ABANDON)
    @Operation(summary = "Ajouter ou supprimer un film de la liste des favoris ou modifier son statut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film ajouté à la liste des favoris ou statut modifié",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ajouter aux Favoris", value = "{\"favorite\":false}", description = "Exemple de réponse pour l'ajout aux favoris"),
                                    @ExampleObject(name = "Changer le statut", value = "{\"status\":\"A_REGARDER\"}", description = "Exemple de réponse pour un changement de statut")
                            },
                            schema = @Schema(oneOf = {Favorite.class, Status.class}))
            ),
            @ApiResponse(responseCode = "400", description = "Demande invalide de changement de favori ou de statut", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "Favoris invalide", value = "{\"errorMessage\":\"Favorite must be 0 or 1 (0 => remove, 1 => add)\",\"status\":400}"),
                            @ExampleObject(name = "Statut invalide", value = "{\"errorMessage\":\"Status must be 0, 1, 2 or 3 (0 => A_REGARDER, 1 => EN_COURS, 2 => VU, 3 => ABANDON)\",\"status\":400}")
                    },
                    schema = @Schema(implementation = ErrorMessage.class)
            )),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Movie not found\",\"status\":404}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping("/movies/{id}")
    public ResponseEntity<Return> getFrontMovie(
            @PathVariable Long id,
            @RequestParam(required = false) Integer favorite,
            @RequestParam(required = false) Integer status,
            Authentication authentication
    ) throws MovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        if (favorite == null && status == null) {
            throw new MovieException("Favorite or status must be provided", HttpStatus.BAD_REQUEST);
        }

        if (favorite != null && status != null) {
            throw new MovieException("Favorite and status cannot be provided at the same time", HttpStatus.BAD_REQUEST);
        }

        if (favorite != null) {
            return updateFavorite(id, favorite, userEntity);
        } else {
            return updateStatus(id, status, userEntity);
        }
    }

    private ResponseEntity<Return> updateFavorite(Long id, Integer favorite, UserEntity userEntity) throws MovieException {
        if (favorite < 0 || favorite > 1) {
            throw new MovieException("Favorite must be 0 or 1(0 => remove, 1 => add)", HttpStatus.BAD_REQUEST);
        }
        boolean fav = playlistMovieService.updateFavorite(id, favorite, userEntity.getId());
        Favorite favoriteResponse = new Favorite(fav);

        return ResponseEntity.ok(favoriteResponse);
    }

    private ResponseEntity<Return> updateStatus(Long id, Integer status, UserEntity userEntity) throws MovieException {
        if (status < 0 || status > 3) {
            throw new MovieException("Status must be 0, 1, 2 or 3 (0 => A_REGARDER, 1 => EN_COURS, 2 => VU, 3 => ABANDON)", HttpStatus.BAD_REQUEST);
        }
        StatusEnum statusEnum = statusToString(status);
        playlistMovieService.updateStatus(id, statusEnum, userEntity.getId());
        Status s = new Status(statusEnum);
        return ResponseEntity.ok(s);
    }

    private StatusEnum statusToString(int status) {
        return switch (status) {
            case 0 -> StatusEnum.A_REGARDER;
            case 1 -> StatusEnum.EN_COURS;
            case 2 -> StatusEnum.VU;
            case 3 -> StatusEnum.ABANDON;
            default -> null;
        };
    }

    interface Return {
    }

    public record Favorite(boolean favorite) implements Return {
    }

    public record Status(StatusEnum status) implements Return {
    }


    @Operation(summary = "Obtenez tous les films de la playlist de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieGetResponseDTO.class))
            )
    })
    @GetMapping("/movies")
    public ResponseEntity<List<MovieGetResponseDTO>> getAllMoviesByUser(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        //Récupérer la liste de films du user dans le service
        List<MovieGetResponseDTO> playListMovies = playlistMovieService.findAllMoviesByUser(userEntity);

        return ResponseEntity.ok(playListMovies);
    }

    // DELETE /movies/{id}
    @Operation(summary = "Supprimer un film de la playlist de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film supprimé de la playlist"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Movie not found\",\"status\":404}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<ErrorMessage> deleteMovie(@PathVariable Long id, Authentication authentication) throws MovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        playlistMovieService.delete(id, 0, userEntity.getId());

        return ResponseEntity.ok(new ErrorMessage(200, "Movie deleted"));
    }
}
