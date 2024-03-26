package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.GenreEntity;

import java.util.List;

public interface GenreRepository {
    List<GenreEntity> findAllByName(List<String> genres);

    GenreEntity save(GenreEntity genreEntity);
}
