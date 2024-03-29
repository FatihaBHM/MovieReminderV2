package org.lafabrique_epita.infrastructure.playlist_tv;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListTvEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayListTvRepositoryAdapter implements PlayListTvRepository {

    private final PlayListTvJPARepositoryPort playListTvJPARepository;

    public PlayListTvRepositoryAdapter(PlayListTvJPARepositoryPort playListTvJPARepository) {
        this.playListTvJPARepository = playListTvJPARepository;
    }

    @Override
    public PlayListTvEntity save(PlayListTvEntity playListTvEntity) {
        return this.playListTvJPARepository.save(playListTvEntity);
    }

    @Override
    public Optional<PlayListTvEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId) {
        return this.playListTvJPARepository.findByEpisodeIdAndUserId(episodeId, userId);
    }

    @Override
    public List<EpisodeEntity> findEpisodesByUserId(UserEntity user) {
        return this.playListTvJPARepository.findEpisodesByUserId(user);
    }

    @Override
    public boolean existsByEpisodeIdAndUserId(Long serieId, Long userId) {
        return this.playListTvJPARepository.existsByEpisodeIdAndUserId(serieId, userId);
    }

    @Override
    public void delete(PlayListTvEntity playListTvEntity) {
        this.playListTvJPARepository.delete(playListTvEntity);
    }

}
