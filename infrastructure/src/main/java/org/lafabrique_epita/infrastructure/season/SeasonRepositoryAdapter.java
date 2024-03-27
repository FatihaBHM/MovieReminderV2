package org.lafabrique_epita.infrastructure.season;

import org.lafabrique_epita.domain.entities.SeasonEntity;
import org.lafabrique_epita.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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
    public List<SeasonEntity> saveAll(Collection<SeasonEntity> seasonEntity) {
        return this.seasonJPARepositoryPort.saveAll(seasonEntity);
    }
}
