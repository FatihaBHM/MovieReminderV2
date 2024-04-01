package org.lafabrique_epita.application.service.media.playlist_movies;

import org.lafabrique_epita.application.dto.media.GenreDto;
import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDTO;
import org.lafabrique_epita.application.dto.media.movie_get.MovieGetResponseDtoMapper;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostDto;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostDtoMapper;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostDtoResponseMapper;
import org.lafabrique_epita.application.dto.media.movie_post.MoviePostResponseDto;
import org.lafabrique_epita.domain.entities.*;
import org.lafabrique_epita.domain.enums.MovieSort;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.domain.exceptions.MovieException;
import org.lafabrique_epita.domain.repositories.GenreRepository;
import org.lafabrique_epita.domain.repositories.MovieRepository;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaylistMovieServiceAdapter implements PlaylistMovieServicePort {
    private static final String MOVIE_NOT_FOUND = "Film introuvable";
    private final PlayListMovieRepository playListMovieRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public PlaylistMovieServiceAdapter(PlayListMovieRepository playListMovieRepository, MovieRepository movieRepository, GenreRepository genreRepository) {
        this.playListMovieRepository = playListMovieRepository;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
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
                throw new MovieException("Le film existe déjà dans la playlist", HttpStatus.CONFLICT);
            }
        } else {
            movie = MoviePostDtoMapper.convertToMovieEntity(moviePostDto);
            // vérifier si les genres existent déjà en base par leur 'name' et dans ce cas save le genre qui n'existe pas puis récupérer tous les genres pour les envoyer dans movie
            List<String> genres = moviePostDto.genres().stream().map(GenreDto::name).toList();
            List<GenreEntity> genreEntities = new ArrayList<>(genreRepository.findAllByName(genres));
            for (GenreEntity genre : movie.getGenres()) {
                if (genreEntities.stream().noneMatch(genreEntity -> genreEntity.getName().equals(genre.getName()))) {
                    genreEntities.add(genreRepository.save(genre));
                }
            }
            movie.setGenres(genreEntities);
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
            throw new MovieException(MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        PlayListMovieID playListMovieID = new PlayListMovieID(idMovie, idUser);
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);

        if (playListMovieEntity.isEmpty()) {
            throw new MovieException(MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
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
        throw new MovieException(MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long movieId, int i, Long userId) throws MovieException {
        PlayListMovieID playListMovieID = new PlayListMovieID(movieId, userId);
        Optional<PlayListMovieEntity> playListMovieEntity = this.playListMovieRepository.findByMovieIdAndUserId(playListMovieID);
        if (playListMovieEntity.isPresent()) {
            this.playListMovieRepository.delete(playListMovieEntity.get());
            int count = this.playListMovieRepository.countByMovieId(movieId);
            if (count == 0) {
                this.movieRepository.deleteById(movieId);
            }
        } else {
            throw new MovieException(MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

//    @Override
//    public MovieGetResponseDTO findMovieByIdTmdb(Long idTmdb) throws MovieException {
//        Optional<MovieEntity> movieEntity = this.movieRepository.findByIdTmdb(idTmdb);
//        if (movieEntity.isPresent()) {
//            return MovieGetResponseDtoMapper.convertToMovieDto(movieEntity.get(), null);
//        }
//        throw new MovieException(MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
//    }

    @Override
    public List<MovieGetResponseDTO> findAllMoviesByUser(UserEntity user, MovieSort sort) {
        List<PlayListMovieEntity> playlists = playListMovieRepository.findMoviesByUserId(user);

        Comparator<MovieGetResponseDTO> comparator = Comparator.comparing(MovieGetResponseDTO::createdDate);
        if (sort == MovieSort.CREATED_DATE_DESC) {
            comparator = comparator.reversed();
        }

        return playlists.stream()
                .map((PlayListMovieEntity playListMovieEntity) -> MovieGetResponseDtoMapper.convertToMovieDto(playListMovieEntity.getMovie(), playListMovieEntity))
                .sorted(comparator)
                .toList();
    }

}
