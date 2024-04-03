package org.lafabrique_epita.infrastructure.season;

import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SeasonRepositoryAdapter implements SeasonRepository {

    private final SeasonJPARepositoryPort seasonJPARepositoryPort;

    public SeasonRepositoryAdapter(SeasonJPARepositoryPort seasonJPARepositoryPort) {
        this.seasonJPARepositoryPort = seasonJPARepositoryPort;
    }

    @Override
    public SeasonEntity save(SeasonEntity seasonEntity) {
        return this.seasonJPARepositoryPort.save(seasonEntity);
    }

    @Override
    public Optional<SeasonEntity> findByIdTmdb(Long idTmdbSeason) {
        return this.seasonJPARepositoryPort.findByIdTmdb(idTmdbSeason);
    }

    @Override
    public void delete(SeasonEntity season) {
        this.seasonJPARepositoryPort.delete(season);
    }
}
