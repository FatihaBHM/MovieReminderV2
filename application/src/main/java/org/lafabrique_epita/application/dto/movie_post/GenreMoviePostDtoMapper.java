package org.lafabrique_epita.application.dto.movie_post;

import org.lafabrique_epita.domain.entities.GenreEntity;

public class GenreMoviePostDtoMapper {
    private GenreMoviePostDtoMapper() {
    }

    public static GenreEntity convertToGenreEntity(GenreMovieDto genreMovieDto) {
        GenreEntity genre = new GenreEntity();
        genre.setIdTmdb(genreMovieDto.id());
        genre.setName(genreMovieDto.name());
        return genre;
    }

    public static GenreMovieDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreMovieDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }
}
