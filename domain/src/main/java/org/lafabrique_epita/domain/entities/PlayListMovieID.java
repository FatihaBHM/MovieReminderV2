package org.lafabrique_epita.domain.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayListMovieID implements Serializable {
    private Long movieId;
    private Long userId;

}
