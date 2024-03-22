package org.lafabrique_epita.exposition.api.media;

import jakarta.validation.Valid;
import org.lafabrique_epita.application.service.media.MovieServiceImpl;
import org.lafabrique_epita.application.service.media.playlist_movies.PlaylistMovieServiceImpl;
import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieEntity;
import org.lafabrique_epita.domain.entities.PlayListMovieID;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDto;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDtoMapper;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostDtoResponseMapper;
import org.lafabrique_epita.exposition.dto.movie_post.MoviePostResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceImpl movieService;

    private final PlaylistMovieServiceImpl playlistMovieService;


    public MovieController(MovieServiceImpl movieService, PlaylistMovieServiceImpl playlistMovieService) {
        this.movieService = movieService;
        this.playlistMovieService = playlistMovieService;
    }

    @PostMapping("/movies")
    public ResponseEntity<MoviePostResponseDto> getFrontMovie(@Valid @RequestBody MoviePostDto moviePostDto, Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        MovieEntity movie = MoviePostDtoMapper.convertToMovieEntity(moviePostDto);
        MovieEntity movieEntity = movieService.save(movie);

        PlayListMovieID playListMovieID = new PlayListMovieID(movie.getId(), userEntity.getId());

        PlayListMovieEntity playListMovieEntity = new PlayListMovieEntity();
        playListMovieEntity.setId(playListMovieID);
        playListMovieEntity.setMovie(movie);
        playListMovieEntity.setUser(userEntity);
        playListMovieEntity.setStatus(StatusEnum.A_REGARDER);
        playlistMovieService.save(playListMovieEntity);

        return ResponseEntity.ok(MoviePostDtoResponseMapper.convertToMovieDto(movieEntity));
    }

    // /movies/{id}?favorite=1
    @GetMapping("/movies/{id}")
    public ResponseEntity<Favorite> getFrontMovie(
            @PathVariable Long id,
            @RequestParam Integer favorite,
            Authentication authentication
    ) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        PlayListMovieID playListMovieID = new PlayListMovieID(id, userEntity.getId());

        PlayListMovieEntity playListMovieEntity = playlistMovieService.findByUserAndByMovie(playListMovieID);

        playListMovieEntity.setFavorite(favorite == 1);

        playlistMovieService.save(playListMovieEntity);



        Favorite favoriteResponse = new Favorite(playListMovieEntity.isFavorite());

        return ResponseEntity.ok(favoriteResponse);
    }

    public record Favorite(boolean favorite) {}

}
