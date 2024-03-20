package infrastructure.playlist_movie;

import infrastructure.movie.MovieJPARepository;
import org.lafabrique_epita.domain.repositories.PlayListMovieRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListMovieRepositoryAdapter implements PlayListMovieRepository {

        private final MovieJPARepository movieJPARepository;

        public PlayListMovieRepositoryAdapter(MovieJPARepository movieJPARepository) {
            this.movieJPARepository = movieJPARepository;
        }
}
