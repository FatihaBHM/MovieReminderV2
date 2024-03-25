package org.lafabrique_epita.exposition.dto.movie_get;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lafabrique_epita.exposition.dto.movie_post.CommentDto;
import org.lafabrique_epita.exposition.dto.movie_post.GenreMovieDto;

import java.time.LocalDate;
import java.util.List;

public record MovieGetResponseDTO(
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

        List<GenreMovieDto> genres,

        @JsonProperty("release_date")
        LocalDate releaseDate,

        List<CommentDto> comments

) {
}
