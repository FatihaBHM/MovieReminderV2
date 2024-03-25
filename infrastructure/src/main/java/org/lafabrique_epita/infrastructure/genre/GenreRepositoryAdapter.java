package org.lafabrique_epita.infrastructure.genre;

import org.lafabrique_epita.domain.repositories.GenreRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepositoryAdapter implements GenreRepository {

    private final GenreJPARepositoryPort genreJPARepository;

    public GenreRepositoryAdapter(GenreJPARepositoryPort genreJPARepository) {
        this.genreJPARepository = genreJPARepository;
    }
}
