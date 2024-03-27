package org.lafabrique_epita.application.service.media.serie;

import jakarta.transaction.Transactional;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDto;
import org.lafabrique_epita.application.dto.media.serie_get.SerieGetResponseDtoMapper;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SerieServiceAdapter implements SerieServicePort {

    private final SerieRepository serieRepository;

    public SerieServiceAdapter(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public SerieEntity save(SerieEntity serie) {
        return serieRepository.save(serie);
    }

    public List<SerieEntity> getAll() {
        return serieRepository.findAll();
    }

    @Override
    public SerieGetResponseDto findSerieByIdTmdb(Long idTmdb) {
        return this.serieRepository.findByIdTmdb(idTmdb)
                .map(SerieGetResponseDtoMapper::convertToSerieDto)
                .orElse(null);
    }
}
