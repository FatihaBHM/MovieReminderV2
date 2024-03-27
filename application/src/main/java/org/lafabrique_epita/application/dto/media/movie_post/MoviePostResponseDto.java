package org.lafabrique_epita.application.dto.media.movie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record MoviePostResponseDto(
        Long id,

        @JsonProperty("id_tmdb")
        Long idTmdb,

        @NotNull
        @NotBlank
        String title,

        Integer duration,

        String overview,

        @JsonProperty("poster_path")
        String backdropPath,

        Float score,

        List<GenreDto> genres,

        @JsonProperty("release_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate releaseDate,

        List<CommentDto> comments

) {
}
