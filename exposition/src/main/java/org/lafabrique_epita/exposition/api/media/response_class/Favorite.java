package org.lafabrique_epita.exposition.api.media.response_class;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Favorite(
        @JsonProperty("media_id")
        Long mediaId,

        @JsonProperty("favorite")
        boolean favorite
)
        implements ResponseStatusAndFavorite {
}
