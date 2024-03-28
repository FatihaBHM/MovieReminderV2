package org.lafabrique_epita.infrastructure.serie;


import org.lafabrique_epita.domain.entities.SerieEntity;
import org.lafabrique_epita.domain.repositories.SerieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SerieRepositoryAdapter implements SerieRepository {

    private final SerieJPARepositoryPort serieJPARepository;

    public SerieRepositoryAdapter(SerieJPARepositoryPort serieJPARepository) {
        this.serieJPARepository = serieJPARepository;
    }

    @Override
    public SerieEntity save(SerieEntity serie) {
        //save comment
        //save genre
        return serieJPARepository.save(serie);
    }

    @Override
    public Optional<SerieEntity> findByIdTmdb(Long aLong) {
        return this.serieJPARepository.findByIdTmdb(aLong);
    }

    @Override
    public List<SerieEntity> findAll() {
        return this.serieJPARepository.findAll();
    }

    @Override
    public Optional<SerieEntity> findById(Long serieId) {
        return this.serieJPARepository.findById(serieId);
    }

    @Override
    public void delete(SerieEntity serie) {
        this.serieJPARepository.delete(serie);
    }
}
