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
import org.lafabrique_epita.application.dto.media.serie_post.EpisodePostDto;
import org.lafabrique_epita.application.service.media.playlist_series.PlaylistTvServiceAdapter;
import org.lafabrique_epita.application.service.media.serie.EpisodeServiceAdapter;
import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.EpisodeException;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.exposition.api.ApiControllerBase;
import org.lafabrique_epita.exposition.api.media.response_class.Favorite;
import org.lafabrique_epita.exposition.api.media.response_class.ResponseStatusAndFavorite;
import org.lafabrique_epita.exposition.api.media.response_class.Status;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Episode", description = "L'API des épisodes")
@RestController
public class EpisodeController extends ApiControllerBase {

    private final PlaylistTvServiceAdapter playlistTvService;
    private final EpisodeServiceAdapter episodeService;

    public EpisodeController(PlaylistTvServiceAdapter playlistTvService, EpisodeServiceAdapter episodeService) {
        this.playlistTvService = playlistTvService;
        this.episodeService = episodeService;
    }

    @Operation(summary = "Ajouter un épisode à la liste des favoris ou modifier le statut",
            parameters = {
                    @Parameter(name = "idTmdbSeason", description = "Id TMDB de la saison", example = "1154", schema = @Schema(implementation = Long.class)),
                    @Parameter(name = "favorite", description = "Ajouter ou supprimer un épisode de la liste des favoris (0=>retrait, 1=>ajout)", schema = @Schema(implementation =
                            Integer.class)),
                    @Parameter(name = "status", description = "Modifier le statut du épisode (0=>A_REGARDER, 1=>EN_COURS, 2=>VU, 3=>ABANDON)", example = "0", schema = @Schema(implementation =
                            Integer.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objet EpisodePostDto",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EpisodePostDto.class),
                            examples = @ExampleObject(value = "{\"id_tmdb\":11,\"title\":\"The Lost Colony\",\"overview\":\"The crew discovers a colony thought to be lost for centuries and uncovers a plot that could threaten the universe.\",\"season_number\":2,\"episode_number\":1,\"air_date\":\"2012-05-20\",\"image_path\":\"/galactic_s2_e1.jpg\",\"duration\":50}", name = "Exemple d'objet EpisodePostDto"))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Episode ajouté à la liste des favoris ou statut modifié",
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
    @PostMapping("/episodes/{idTmdbSeason}")
    public ResponseEntity<ResponseStatusAndFavorite> saveEpisode(
            @Valid @RequestBody EpisodePostDto episodePostDto,
            @PathVariable Long idTmdbSeason,
            @RequestParam(required = false) Integer favorite,
            @RequestParam(required = false) Integer status,
            Authentication authentication
    ) throws SerieException, EpisodeException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        if (favorite == null && status == null) {
            throw new SerieException("Le favori ou le statut doit être fourni", HttpStatus.BAD_REQUEST);
        }

        if (favorite != null && status != null) {
            throw new SerieException("Le favori et le statut ne peuvent pas être fournis en même temps", HttpStatus.BAD_REQUEST);
        }
        // Ici, on doit récupérer un id d'épisode, pas de saison
        // Mais avant, il sauvegarde l'épisode dans la base de données
        EpisodeEntity episode = this.episodeService.save(episodePostDto, idTmdbSeason);
        this.playlistTvService.save(episode, userEntity);

        if (favorite != null) {
            return updateFavorite(episode.getId(), favorite, userEntity);
        } else {
            return updateStatus(episode.getId(), status, userEntity);
        }
    }

    private ResponseEntity<ResponseStatusAndFavorite> updateFavorite(Long episodeId, Integer favorite, UserEntity userEntity) throws SerieException {
        if (favorite < 0 || favorite > 1) {
            throw new SerieException("Le favori doit être 0 ou 1 (0 => supprimer, 1 => ajouter)", HttpStatus.BAD_REQUEST);
        }
        boolean fav = playlistTvService.updateFavorite(episodeId, favorite, userEntity.getId());
        Favorite favoriteResponse = new Favorite(fav);

        return ResponseEntity.ok(favoriteResponse);
    }

    private ResponseEntity<ResponseStatusAndFavorite> updateStatus(Long episodeId, Integer status, UserEntity userEntity) throws SerieException {
        if (status < 0 || status > 3) {
            throw new SerieException("Le statut doit être 0, 1, 2 ou 3 (0 => A_REGARDER, 1 => EN_COURS, 2 => VU, 3 => ABANDON)", HttpStatus.BAD_REQUEST);
        }
        StatusEnum statusEnum = statusToString(status);
        playlistTvService.updateStatus(episodeId, statusEnum, userEntity.getId());
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
}
