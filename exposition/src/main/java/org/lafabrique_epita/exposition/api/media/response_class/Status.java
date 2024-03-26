package org.lafabrique_epita.exposition.api.media.response_class;

import org.lafabrique_epita.domain.enums.StatusEnum;

public record Status(StatusEnum status) implements ResponseStatusAndFavorite {
}
