package org.lafabrique_epita.exposition.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MoviePostResponseDto(
        Long id, //A enlever
        Long idTmdb,
        @NotNull
        @NotBlank
        String title,
        Integer duration,
        String overview,
        String backdropPath,
        Float score,
        List<GenreMoviePostResponseDto> genres,
        LocalDate releaseDate,
        List<CommentMoviePostResponseDto> comments

) {
}
