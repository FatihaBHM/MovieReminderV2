package org.lafabrique_epita.infrastructure.episode;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.repositories.EpisodeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EpisodeRepositoryAdapter implements EpisodeRepository {

    private final EpisodeJPARepositoryPort episodeJPARepository;

    public EpisodeRepositoryAdapter(EpisodeJPARepositoryPort commentJpaRepository) {
        this.episodeJPARepository = commentJpaRepository;
    }

    @Override
    public EpisodeEntity save(EpisodeEntity episodeEntity) {
        return this.episodeJPARepository.save(episodeEntity);
    }

    @Override
    public List<EpisodeEntity> saveAll(List<EpisodeEntity> episodeEntity) {
        return this.episodeJPARepository.saveAll(episodeEntity);
    }

    @Override
    public Optional<EpisodeEntity> findByIdTmdb(Long idTmdb) {
        return this.episodeJPARepository.findByIdTmdb(idTmdb);
    }
}
