package org.lafabrique_epita.exposition.dto.movie_post;

import org.lafabrique_epita.domain.entities.GenreEntity;

public class GenreMoviePostDtoMapper {
    private GenreMoviePostDtoMapper() {
    }

    public static GenreEntity convertToGenreEntity(GenreMoviePostDto genreMoviePostDto) {
        GenreEntity genre = new GenreEntity();
        genre.setIdTmdb(genreMoviePostDto.id());
        genre.setName(genreMoviePostDto.name());
        return genre;
    }

    public static GenreMoviePostDto convertToGenreDto(GenreEntity genreEntity) {
        return new GenreMoviePostDto(
                genreEntity.getIdTmdb(),
                genreEntity.getName()
        );
    }
}
