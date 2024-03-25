package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.movie_get.MovieGetResponseDtoMapper;
import org.lafabrique_epita.application.dto.movie_post.MoviePostDto;
import org.lafabrique_epita.application.dto.movie_post.MoviePostDtoMapper;
import org.lafabrique_epita.application.dto.movie_post.MoviePostDtoResponseMapper;
import org.lafabrique_epita.application.dto.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.MovieException;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistMovieServiceAdapter implements PlaylistMovieServicePort {

    private final PlayListMovieRepository playListMovieRepository;
    private final MovieRepository movieRepository;

    public PlaylistMovieServiceAdapter(PlayListMovieRepository playListMovieRepository, MovieRepository movieRepository) {
        this.playListMovieRepository = playListMovieRepository;
        this.movieRepository = movieRepository;
    }


@Override
public MoviePostResponseDto save(MoviePostDto moviePostDto, UserEntity user) throws MovieException {

    // vérifier si l'utilisateur n'a pas déjà ajouté ce film à sa liste
    Optional<MovieEntity> movieEntity = this.movieRepository.findByIdTmdb(moviePostDto.idTmdb());
    MovieEntity movie;
    if (movieEntity.isPresent()) {
        movie = movieEntity.get();
        boolean existsByMovieIdAndUserId = this.playListMovieRepository.existsByMovieIdAndUserId(movie.getId(), user.getId());
        if (existsByMovieIdAndUserId) {
            throw new MovieException("Movie already exists in the playlist", HttpStatus.CONFLICT);
        }
    } else {
        movie = MoviePostDtoMapper.convertToMovieEntity(moviePostDto);
        movie = this.movieRepository.save(movie);
    }

    PlayListMovieEntity playListMovieEntity = createAndSavePlayListMovieEntity(movie, user);
    return MoviePostDtoResponseMapper.convertToMovieDto(playListMovieEntity.getMovie());
}

private PlayListMovieEntity createAndSavePlayListMovieEntity(MovieEntity movie, UserEntity user) {
    PlayListMovieID playListMovieID = new PlayListMovieID(movie.getId(), user.getId());
    PlayListMovieEntity playListMovieEntity = new PlayListMovieEntity();
    playListMovieEntity.setId(playListMovieID);
    playListMovieEntity.setMovie(movie);
    playListMovieEntity.setUser(user);
    playListMovieEntity.setStatus(StatusEnum.A_REGARDER);

    return this.playListMovieRepository.save(playListMovieEntity);
}

    @Override
    public MoviePostResponseDto findByUserAndByMovie(Long movieId, Long userId) {
        PlayListMovieID playListMovieID = new PlayListMovieID(movieId, userId);
        PlayListMovieEntity playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
        if (playListMovieEntity != null) {
            return MoviePostDtoResponseMapper.convertToMovieDto(playListMovieEntity.getMovie());
        }
        return null;
    }

    @Override
    public boolean setFavorite(Long idMovie, Integer favorite, Long idUser) throws MovieException {
        MoviePostResponseDto playListMovieDto = this.findByUserAndByMovie(idMovie, idUser);

        if (playListMovieDto == null) {
            throw new MovieException("Movie not found");
        }

        PlayListMovieID playListMovieID = new PlayListMovieID(idMovie, idUser);
        PlayListMovieEntity playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);

        playListMovieEntity.setFavorite(favorite == 1);

        PlayListMovieEntity m = this.playListMovieRepository.save(playListMovieEntity);
        return m.isFavorite();
    }

    @Override
    public List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user) {
        List<MovieEntity> playlists = playListMovieRepository.findMoviesByUserId(user);

        return playlists.stream()
                .map(MovieGetResponseDtoMapper::convertToMovieDto).toList();
    }

}
