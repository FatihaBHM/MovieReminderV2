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
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
        return playListMovieEntity.map(listMovieEntity -> MoviePostDtoResponseMapper.convertToMovieDto(listMovieEntity.getMovie())).orElse(null);
    }

    @Override
    public boolean updateFavorite(Long idMovie, Integer favorite, Long idUser) throws MovieException {
        MoviePostResponseDto playListMovieDto = this.findByUserAndByMovie(idMovie, idUser);

        if (playListMovieDto == null) {
            throw new MovieException("Movie not found", HttpStatus.NOT_FOUND);
        }

        PlayListMovieID playListMovieID = new PlayListMovieID(idMovie, idUser);
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);

        if (playListMovieEntity.isEmpty()) {
            throw new MovieException("Movie not found", HttpStatus.NOT_FOUND);
        }

        playListMovieEntity.get().setFavorite(favorite == 1);

        PlayListMovieEntity m = this.playListMovieRepository.save(playListMovieEntity.get());
        return m.isFavorite();
    }

    public void updateStatus(Long movieId, StatusEnum status, Long userId) throws MovieException {
        PlayListMovieID playListMovieID = new PlayListMovieID(movieId, userId);
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);

        if (playListMovieEntity.isPresent()) {
            playListMovieEntity.get().setStatus(status);
            this.playListMovieRepository.save(playListMovieEntity.get());
            return;
        }
        throw new MovieException("Movie not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long movieId, int i, Long userId) throws MovieException {
        PlayListMovieID playListMovieID = new PlayListMovieID(movieId, userId);
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
        if (playListMovieEntity.isPresent()) {
            this.playListMovieRepository.delete(playListMovieEntity.get());
        } else {
            throw new MovieException("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public MovieGetResponseDTO findMovieByIdTmdb(Long idTmdb) throws MovieException {
        Optional<MovieEntity> movieEntity = this.movieRepository.findByIdTmdb(idTmdb);
        if (movieEntity.isPresent()) {
            return MovieGetResponseDtoMapper.convertToMovieDto(movieEntity.get());
        }
        throw new MovieException("Movie not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user) {
        List<MovieEntity> playlists = playListMovieRepository.findMoviesByUserId(user);

        return playlists.stream()
                .map(MovieGetResponseDtoMapper::convertToMovieDto).toList();
    }

}
