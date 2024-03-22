package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistMovieServiceImpl implements IPlaylistMovieService {

    private final PlayListMovieRepository playListMovieRepository;

    public PlaylistMovieServiceImpl(PlayListMovieRepository playListMovieRepository) {
        this.playListMovieRepository = playListMovieRepository;
    }


    @Override
    public void save(PlayListMovieEntity playListMovieEntity) {
        this.playListMovieRepository.save(playListMovieEntity);
    }

    @Override
    public PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID) {
        return this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
    }


}
