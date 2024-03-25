package org.lafabrique_epita.application.dto.media;

import org.lafabrique_epita.domain.entities.GenreEntity;

public class GenreDtoMapper {
    private GenreDtoMapper() {
    }

    public static GenreEntity convertToEntity(GenreDto genreMovieDto) {
        GenreEntity genre = new GenreEntity();
        genre.setIdTmdb(genreMovieDto.id());
        genre.setName(genreMovieDto.name());
        return genre;
    }

    public static GenreDto convertToDto(GenreEntity genreEntity) {
        return new GenreDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }
}
