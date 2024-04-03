package org.lafabrique_epita.exposition.api.media.response_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lafabrique_epita.domain.enums.StatusEnum;

public record Status(
        @JsonProperty("tmdb_id")
        Long tmdbId,

        @JsonProperty("status")
        StatusEnum status
) implements ResponseStatusAndFavorite {
}
