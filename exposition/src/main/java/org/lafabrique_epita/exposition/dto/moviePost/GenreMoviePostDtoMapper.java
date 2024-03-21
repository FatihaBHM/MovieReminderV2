package org.lafabrique_epita.exposition.dto.moviePost;

import lombok.RequiredArgsConstructor;
import org.lafabrique_epita.domain.entities.GenreEntity;

@RequiredArgsConstructor
public class GenreMoviePostDtoMapper {
    public static GenreEntity convertToGenreEntity(GenreMoviePostDto genreMoviePostDto) {
        GenreEntity genre = new GenreEntity();
        genre.setIdTmdb(genreMoviePostDto.id());
        genre.setName(genreMoviePostDto.name());
        return genre;
    }
}
