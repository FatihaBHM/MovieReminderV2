package org.lafabrique_epita.infrastructure.playlist_episode;

import org.lafabrique_epita.domain.entities.EpisodeEntity;
import org.lafabrique_epita.domain.entities.PlayListEpisodeEntity;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.repositories.PlayListEpisodeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayListEpisodeRepositoryAdapter implements PlayListEpisodeRepository {

    private final PlayListEpisodeJPARepositoryPort playListTvJPARepository;

    public PlayListEpisodeRepositoryAdapter(PlayListEpisodeJPARepositoryPort playListTvJPARepository) {
        this.playListTvJPARepository = playListTvJPARepository;
    }

    @Override
    public PlayListEpisodeEntity save(PlayListEpisodeEntity playListEpisodeEntity) {
        return this.playListTvJPARepository.save(playListEpisodeEntity);
    }

    @Override
    public Optional<PlayListEpisodeEntity> findByEpisodeIdAndUserId(Long episodeId, Long userId) {
        return this.playListTvJPARepository.findByEpisodeIdAndUserId(episodeId, userId);
    }

    @Override
    public List<EpisodeEntity> findEpisodesByUserId(UserEntity user) {
        return this.playListTvJPARepository.findEpisodesByUserId(user);
    }

    @Override
    public List<PlayListEpisodeEntity> findByEpisodeAndUserIdNot(EpisodeEntity episode, Long userId) {
        return this.playListTvJPARepository.findByEpisodeIdAndUserIdNot(episode.getId(), userId);
    }

    @Override
    public void delete(PlayListEpisodeEntity playListEpisodeEntity) {
        this.playListTvJPARepository.delete(playListEpisodeEntity);
    }

}
