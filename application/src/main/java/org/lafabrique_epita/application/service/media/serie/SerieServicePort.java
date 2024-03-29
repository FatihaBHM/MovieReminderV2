package org.lafabrique_epita.application.service.media.serie;


import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.exceptions.SerieException;

public interface SerieServicePort {
    SerieEntity save(SerieEntity serie);

    SerieGetResponseDto findSerieByIdTmdb(Long idTmdb) throws SerieException;

    void delete(Long serieId, Long userId) throws SerieException;
}
