package org.lafabrique_epita.application.dto.media.movie_get;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lafabrique_epita.application.dto.media.CommentDto;
import org.lafabrique_epita.application.dto.media.GenreDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MovieGetResponseDTO(
        Long id,

        @JsonProperty("id_tmdb")
        Long idTmdb,

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

        Integer status,

        @JsonProperty("-")
        LocalDateTime createdDate,

        List<CommentDto> comments

) {

}
