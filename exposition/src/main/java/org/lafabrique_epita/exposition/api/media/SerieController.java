package org.lafabrique_epita.exposition.api.media;

import ch.qos.logback.core.status.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostResponseDto;
import org.lafabrique_epita.application.service.media.serie.SerieServicePort;
import org.lafabrique_epita.application.service.media.playlist_series.PlaylistTvServiceAdapter;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.SerieException;
import org.lafabrique_epita.exposition.api.ApiControllerBase;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serie", description = "The Serie API")
@RestController
public class SerieController extends ApiControllerBase {

    private final PlaylistTvServiceAdapter playlistTvService;

    private final SerieServicePort serieService;


    public SerieController(PlaylistTvServiceAdapter playlistTvService, SerieServicePort serieService) {
        this.playlistTvService = playlistTvService;
        this.serieService = serieService;
    }

   @PostMapping("/series")
    public ResponseEntity<SeriePostResponseDto> getFrontSerie(@Valid @RequestBody SeriePostDto seriePostDto, Authentication authentication) throws SerieException {
       UserEntity userEntity = (UserEntity) authentication.getPrincipal();

       SeriePostResponseDto serieDto = playlistTvService.save(seriePostDto, userEntity);
       return ResponseEntity.ok(serieDto);

//       SerieEntity serie = SeriePostDtoMapper.convertToSerieEntity(seriePostDto);
//       SerieEntity serieEntity = serieService.save(serie);
//
//       PlayListTvID playListTvID = new PlayListTvID(serie.getId(), userEntity.getId());
//
//       PlayListTvEntity playListTvEntity = new PlayListTvEntity();
//       playListTvEntity.setId(playListTvID);
//
//       return null;

   }

   @PatchMapping("series/{id}")
    public ResponseEntity<Return>getFrontSerie(
            @PathVariable Long id,
            @RequestParam(required = false) Integer favorite,
            @RequestParam(required = false) Integer status,
            Authentication authentication
   ) throws SerieException {
       UserEntity userEntity = (UserEntity) authentication.getPrincipal();

       if (favorite == null && status == null) {
           throw new SerieException("Favorite or status must be provided", HttpStatus.BAD_REQUEST);
       }

       if (favorite != null && status != null) {
           throw new SerieException("Favorite and status cannot be provided at the same time", HttpStatus.BAD_REQUEST);
       }

       if (favorite != null) {
           return updateFavorite(id, favorite, userEntity);
       } else {
           return updateStatus(id, status, userEntity);
       }

   }

   private ResponseEntity<Return> updateFavorite(Long id, Integer favorite, UserEntity userEntity) throws SerieException {
                if (favorite < 0 || favorite > 1) {
                    throw new SerieException("Favorite must be 0 or 1(0 => remove, 1=> add)", HttpStatus.BAD_REQUEST);
                }
                boolean fav = playlistTvService.updateFavorite(id, favorite, userEntity.getId());
              Favorite favoriteResponse = new Favorite(fav);

                return ResponseEntity.ok(favoriteResponse);
    }

    private ResponseEntity<Return> updateStatus(Long id, Integer status, UserEntity userEntity) throws SerieException {
        if (status < 0 || status > 3) {
            throw new SerieException("Status must be 0, 1, 2 or 3 (0 => A_REGARDER, 1 => EN_COURS, 2 => VU, 3 => ABANDON)", HttpStatus.BAD_REQUEST);
        }
        StatusEnum statusEnum = statusToString(status);
        playlistTvService.updateStatus(id, statusEnum, userEntity.getId());
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

    @GetMapping("/series")
    public ResponseEntity<List<SerieGetResponseDto>> getAllSeriesByUser(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        //Récupérer la liste des series by user dans le service
        List<SerieGetResponseDto> playListSeries = playlistTvService.findAllEpisodesByUser(userEntity);

        return ResponseEntity.ok(playListSeries);
    }

    //DELETE/series/{id}
    @DeleteMapping("/series{id}")
    public ResponseEntity<ErrorMessage> deleteSerie(@PathVariable Long id, Authentication authentication) throws SerieException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        playlistTvService.delete(id, 0, userEntity.getId());

        return ResponseEntity.ok(new ErrorMessage(200, "Serie deleted"));
    }

    // GET/series/{idTmdb}

    @GetMapping("/series/{idTmdb}")
    public ResponseEntity<SerieGetResponseDto> getSerieByIdTmdb(@PathVariable Long idTmdb) throws SerieException {
        SerieGetResponseDto serie = serieService.findSerieByIdTmdb(idTmdb);
        if (serie == null) {
            throw new SerieException("Serie not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(serie);
    }


}
