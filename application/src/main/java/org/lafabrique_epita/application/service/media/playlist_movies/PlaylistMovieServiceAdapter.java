package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.exceptions.PlayListMovieException;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaylistMovieServiceAdapter implements PlaylistMovieServicePort {

    private final PlayListMovieRepository playListMovieRepository;

    public PlaylistMovieServiceAdapter(PlayListMovieRepository playListMovieRepository) {
        this.playListMovieRepository = playListMovieRepository;
    }


    @Override
    public void save(PlayListMovieEntity playListMovieEntity) throws PlayListMovieException {
        this.playListMovieRepository.save(playListMovieEntity);
    }

    @Override
    public PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID) {
        return this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
    }


}
