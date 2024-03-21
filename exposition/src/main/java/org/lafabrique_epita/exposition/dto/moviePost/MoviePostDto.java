package org.lafabrique_epita.exposition.dto.moviePost;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record MoviePostDto(

        @NotNull
        @PositiveOrZero
        Long id,

        @NotNull
        @NotBlank
        String titre,

        @PositiveOrZero
        Integer duration,

        String resume,

        String imageLandscape,

        Float score,

        List<GenreMoviePostDto> genres,

        LocalDate date

) {
}