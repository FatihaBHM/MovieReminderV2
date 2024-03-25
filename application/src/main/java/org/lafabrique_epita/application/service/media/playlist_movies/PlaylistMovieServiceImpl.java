package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDtoMapper;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.lafabrique_epita.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistMovieServiceImpl implements IPlaylistMovieService {

    private final PlayListMovieRepository playListMovieRepository;
    private final UserRepository userRepository;

    public PlaylistMovieServiceImpl(PlayListMovieRepository playListMovieRepository, UserRepository userRepository) {
        this.playListMovieRepository = playListMovieRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void save(PlayListMovieEntity playListMovieEntity) {
        this.playListMovieRepository.save(playListMovieEntity);
    }

    @Override
    public PlayListMovieEntity findByUserAndByMovie(PlayListMovieID playListMovieID) {
        return this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
    }

    @Override
    public List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user) {
        List<MovieEntity> playlists = playListMovieRepository.findMoviesByUserId(user);

        return playlists.stream()
                .map(MovieGetResponseDtoMapper::convertToMovieDto).toList();
    }

}
