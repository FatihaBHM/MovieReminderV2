package org.lafabrique_epita.exposition.api.media;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostDto;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.application.service.media.movie.MovieServicePort;
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceAdapter;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.MovieSort;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.MovieException;
import org.lafabrique_epita.exposition.api.ApiControllerBase;
import org.lafabrique_epita.exposition.api.media.response_class.Favorite;
import org.lafabrique_epita.exposition.api.media.response_class.ResponseStatusAndFavorite;
import org.lafabrique_epita.exposition.api.media.response_class.Status;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movie", description = "L'API du film")
@RestController
public class MovieController extends ApiControllerBase {

    private final PlaylistMovieServiceAdapter playlistMovieService;

    private final MovieServicePort movieService;

    public MovieController(PlaylistMovieServiceAdapter playlistMovieService, MovieServicePort movieService) {
        this.playlistMovieService = playlistMovieService;
        this.movieService = movieService;
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
    @Operation(summary = "Ajouter ou supprimer un film de la liste des favoris ou modifier son statut",
            parameters = {
                    @Parameter(name = "id", description = "Id du film", example = "1", schema = @Schema(implementation = Long.class)),
                    @Parameter(name = "favorite", description = "Ajouter ou supprimer un film de la liste des favoris (0=>retrait, 1=>ajout)", example = "1", schema = @Schema(implementation =
                            Integer.class)),
                    @Parameter(name = "status", description = "Modifier le statut du film (0=>A_REGARDER, 1=>EN_COURS, 2=>VU, 3=>ABANDON)", example = "0", schema = @Schema(implementation = Integer.class))
            })
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
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Film introuvable\",\"status\":404}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PatchMapping("/movies/{id}")
    public ResponseEntity<ResponseStatusAndFavorite> getFrontMovie(
            @PathVariable Long id,
            @RequestParam(required = false) Integer favorite,
            @RequestParam(required = false) Integer status,
            Authentication authentication
    ) throws MovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        if (favorite == null && status == null) {
            throw new MovieException("Le favori ou le statut doit être fourni", HttpStatus.BAD_REQUEST);
        }

        if (favorite != null && status != null) {
            throw new MovieException("Le favori et le statut ne peuvent pas être fournis en même temps", HttpStatus.BAD_REQUEST);
        }

        if (favorite != null) {
            return updateFavorite(id, favorite, userEntity);
        } else {
            return updateStatus(id, status, userEntity);
        }
    }

    private ResponseEntity<ResponseStatusAndFavorite> updateFavorite(Long id, Integer favorite, UserEntity userEntity) throws MovieException {
        if (favorite < 0 || favorite > 1) {
            throw new MovieException("Le favori doit être 0 ou 1 (0 => supprimer, 1 => ajouter)", HttpStatus.BAD_REQUEST);
        }
        boolean fav = playlistMovieService.updateFavorite(id, favorite, userEntity.getId());
        Favorite favoriteResponse = new Favorite(fav);

        return ResponseEntity.ok(favoriteResponse);
    }

    private ResponseEntity<ResponseStatusAndFavorite> updateStatus(Long id, Integer status, UserEntity userEntity) throws MovieException {
        if (status < 0 || status > 3) {
            throw new MovieException("Le statut doit être 0, 1, 2 ou 3 (0 => A_REGARDER, 1 => EN_COURS, 2 => VU, 3 => ABANDON)", HttpStatus.BAD_REQUEST);
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

    @Operation(summary = "Obtenez tous les films de la playlist de l'utilisateur connecté", parameters = {
            @Parameter(name = "sort", description = "Trier les films par date de création (asc). Si aucun paramète, descendant par défaut", example = "asc", schema = @Schema(implementation =
                    String.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieGetResponseDTO.class))
            )
    })
    @GetMapping("/movies")
    public ResponseEntity<List<MovieGetResponseDTO>> getAllMoviesByUser(
            @RequestParam(required = false) String sort,
            Authentication authentication
    ) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        MovieSort movieSort = MovieSort.CREATED_DATE_DESC;
        if (sort != null && sort.equalsIgnoreCase("asc")) {
            movieSort = MovieSort.CREATED_DATE_ASC;
        }

        //Récupérer la liste de films du user dans le service
        List<MovieGetResponseDTO> playListMovies = playlistMovieService.findAllMoviesByUser(userEntity, movieSort);

        return ResponseEntity.ok(playListMovies);
    }

    @Operation(summary = "Supprimer un film de la playlist de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film supprimé de la playlist"),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Film introuvable\",\"status\":404}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<ErrorMessage> deleteMovie(@PathVariable Long id, Authentication authentication) throws MovieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        playlistMovieService.delete(id, 0, userEntity.getId());

        return ResponseEntity.ok(new ErrorMessage(200, "Film supprimé"));
    }

    @Operation(summary = "Obtenir un film par son id TMDB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film trouvé",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieGetResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Film introuvable", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"errorMessage\":\"Film introuvable\",\"status\":404}"),
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping("/movies/{idTmdb}")
    public ResponseEntity<MovieGetResponseDTO> getMovieByIdTmdb(@PathVariable Long idTmdb) throws MovieException {
        MovieGetResponseDTO movie = movieService.findMovieByIdTmdb(idTmdb);
        if (movie == null) {
            throw new MovieException("Film introuvable", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(movie);
    }
}
