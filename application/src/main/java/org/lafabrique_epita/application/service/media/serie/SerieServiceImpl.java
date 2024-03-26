package org.lafabrique_epita.application.service.media.serie;

import jakarta.transaction.Transactional;
import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SerieServiceImpl implements ISerieService{

    private final SerieRepository serieRepository;

    public SerieServiceImpl(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    public SerieEntity save(SerieEntity serie) {
        return serieRepository.save(serie);
    }


}
