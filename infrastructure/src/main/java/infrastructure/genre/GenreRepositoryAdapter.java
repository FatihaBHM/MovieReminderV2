package infrastructure.genre;

import org.lafabrique_epita.domain.repositories.GenreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepositoryAdapter implements GenreRepository {

    private final GenreJPARepository genreJPARepository;

    public GenreRepositoryAdapter(GenreJPARepository genreJPARepository) {
        this.genreJPARepository = genreJPARepository;
    }
}
