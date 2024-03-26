package org.lafabrique_epita.infrastructure.episode;

import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EpisodeRepositoryAdapter implements EpisodeRepository {

    private final EpisodeJPARepositoryPort episodeJPARepository;

    public EpisodeRepositoryAdapter(EpisodeJPARepositoryPort commentJpaRepository) {
        this.episodeJPARepository = commentJpaRepository;
    }
}
