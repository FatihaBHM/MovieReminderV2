package org.lafabrique_epita.exposition.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MoviePostDto(

        Long id, //A enlever?
        @NotNull
        @NotBlank
        String titre,
        Integer duration,
        String resume,
        String imageLandscape,

        Float score,
        @NotNull
        @NotBlank
        List<GenreMoviePostDto> genres,
        LocalDate date,
        @NotNull
        @NotBlank
        List<CommentMoviePostDto> review

) {}
