package org.lafabrique_epita.exposition.dto.movie_post;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MoviePostResponseDto(
        @Schema(
                description = "The id of the movie in the database",
                example = "1"
        )
        Long id,

        @Schema(
                description = "The id of the movie in the TMDB database",
                example = "1"
        )
        @JsonProperty("id_tmdb")
        Long idTmdb,

        @Schema(
                description = "The title of the movie",
                example = "The Shawshank Redemption"
        )
        @NotNull
        @NotBlank
        String title,

        @Schema(
                description = "The duration of the movie in minutes",
                example = "142"
        )
        Integer duration,

        @Schema(
                description = "The overview of the movie",
                example = "Two imprisoned"
        )
        String overview,

        @Schema(
                description = "The backdrop path of the movie",
                example = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"
        )
        @JsonProperty("poster_path")
        String backdropPath,

        @Schema(
                description = "The score of the movie",
                example = "9.3"
        )
        Float score,

        @Schema(
                description = "The genres of the movie"
        )
        List<GenreMoviePostDto> genres,

        @Schema(
                description = "The release date of the movie",
                example = "1994-09-23"
        )
        @JsonProperty("release_date")
        LocalDate releaseDate,

        @Schema(
                description = "The comments of the movie"
        )
        List<CommentDto> comments

) {
}
