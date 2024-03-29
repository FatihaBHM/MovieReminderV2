package org.lafabrique_epita.exposition.api.media;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostResponseDto;
import org.lafabrique_epita.application.service.media.playlist_series.PlaylistTvServiceAdapter;
import org.lafabrique_epita.application.service.media.serie.SerieServicePort;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.exposition.api.ApiControllerBase;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serie", description = "L'API Série")
@RestController
public class SerieController extends ApiControllerBase {

    private final PlaylistTvServiceAdapter playlistTvService;

    private final SerieServicePort serieService;


    public SerieController(PlaylistTvServiceAdapter playlistTvService, SerieServicePort serieService) {
        this.playlistTvService = playlistTvService;
        this.serieService = serieService;
    }

    @Operation(summary = "Ajouter une série", description = "Ajouter une série à la playlist de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Série ajoutée à la playlist de l'utilisateur",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeriePostResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Série non ajouté à la playlist", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @PostMapping("/series")
    public ResponseEntity<SeriePostResponseDto> getFrontSerie(@Valid @RequestBody SeriePostDto seriePostDto, Authentication authentication) throws SerieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        SeriePostResponseDto serieDto = playlistTvService.save(seriePostDto, userEntity);
        return ResponseEntity.ok(serieDto);
    }

    @Operation(summary = "Récupérer la liste des séries ajoutées par l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des séries ajoutées par l'utilisateur",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieGetResponseDto.class))
            )
    })
    @GetMapping("/series")
    public ResponseEntity<List<SerieGetResponseDto>> getAllSeriesByUser(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(playlistTvService.findAllEpisodesByUser(userEntity));
    }

    @Operation(summary = "Supprimer une série de la liste des favoris", parameters = {
            @Parameter(name = "id", description = "Id de la série", example = "1", schema = @Schema(implementation = Long.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Série supprimée de la liste des favoris",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "Série introuvable", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @DeleteMapping("/series/{id}")
    public ResponseEntity<ErrorMessage> deleteSerie(@PathVariable Long id, Authentication authentication) throws SerieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        serieService.delete(id, userEntity.getId());

        return ResponseEntity.ok(new ErrorMessage(200, "Série supprimée"));
    }

    @Operation(summary = "Récupérer une série par son idTmdb", parameters = {
            @Parameter(name = "idTmdb", description = "Id de la série sur TMDB", example = "1", schema = @Schema(implementation = Long.class))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie trouvée",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieGetResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Serie introuvable", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)
            ))
    })
    @GetMapping("/series/{idTmdb}")
    public ResponseEntity<SerieGetResponseDto> getSerieByIdTmdb(@PathVariable Long idTmdb) throws SerieException {
        SerieGetResponseDto serie = serieService.findSerieByIdTmdb(idTmdb);
        if (serie == null) {
            throw new SerieException("Série introuvable", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(serie);
    }


}
