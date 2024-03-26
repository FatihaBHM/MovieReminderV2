package org.lafabrique_epita.exposition.api.media;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDto;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostDtoMapper;
import org.lafabrique_epita.application.dto.media.serie_post.SeriePostResponseDto;
import org.lafabrique_epita.application.service.media.serie.SerieServiceImpl;
import org.lafabrique_epita.domain.entities.PlayListTvEntity;
import org.lafabrique_epita.domain.entities.PlayListTvID;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Serie", description = "The Serie API")
@RestController
public class SerieController {

    private final SerieServiceImpl serieService;


    public SerieController(SerieServiceImpl serieService) {
        this.serieService = serieService;
    }

   @PostMapping("/series")
    public ResponseEntity<SeriePostResponseDto> getFrontSerie(@Valid @RequestBody SeriePostDto seriePostDto, Authentication authentication) {
       UserEntity userEntity = (UserEntity) authentication.getPrincipal();

       SerieEntity serie = SeriePostDtoMapper.convertToSerieEntity(seriePostDto);
       SerieEntity serieEntity = serieService.save(serie);

       PlayListTvID playListTvID = new PlayListTvID(serie.getId(), userEntity.getId());

       PlayListTvEntity playListTvEntity = new PlayListTvEntity();
       playListTvEntity.setId(playListTvID);

       return null;

   }

}
