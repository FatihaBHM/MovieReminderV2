package org.lafabrique_epita.application.dto.media;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreDto(
        /*
        Request => idTmdb
        Response => idTmdb
         */
        Long id,

        @NotNull
        @NotBlank
        String name
) {}
