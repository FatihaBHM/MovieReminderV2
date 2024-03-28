package org.lafabrique_epita.infrastructure.genre;

import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.domain.entities.GenreEntity;
import org.lafabrique_epita.domain.repositories.GenreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class GenreRepositoryAdapter implements GenreRepository {

    private final GenreJPARepositoryPort genreJPARepository;

    public GenreRepositoryAdapter(GenreJPARepositoryPort genreJPARepository) {
        this.genreJPARepository = genreJPARepository;
    }

    @Override
    public List<GenreEntity> findAllByName(List<String> genres) {
        return this.genreJPARepository.findAll()
                .stream()
                .filter(genre -> genres.contains(genre.getName()))
                .toList();
    }

    @Override
    public GenreEntity save(GenreEntity genreEntity) {
        log.warn("Genre {} introuvable dans la base de données, création", genreEntity);
        return this.genreJPARepository.save(genreEntity);
    }
}
