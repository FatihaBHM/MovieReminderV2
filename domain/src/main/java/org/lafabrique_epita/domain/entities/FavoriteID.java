package org.lafabrique_epita.domain.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FavoriteID implements Serializable {

    private Long userId;
    private Long movieId;
    private Long serieId;

}
