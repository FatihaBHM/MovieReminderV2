package org.lafabrique_epita.application.service.media.playlist_series;

import org.lafabrique_epita.domain.entities.PlayListTvEntity;
import org.lafabrique_epita.domain.repositories.PlayListTvRepository;
import org.lafabrique_epita.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistTvServiceImpl implements IPlaylistTvService {
    private final PlayListTvRepository playListTvRepository;

    private final UserRepository userRepository;

    public PlaylistTvServiceImpl(PlayListTvRepository playListTvRepository, UserRepository userRepository) {
        this.playListTvRepository = playListTvRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(PlayListTvEntity playListTvEntity) {
        this.playListTvRepository.save(playListTvEntity);
    }
}
