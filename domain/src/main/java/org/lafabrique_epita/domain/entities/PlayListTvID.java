package org.lafabrique_epita.domain.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayListTvID implements Serializable {
    private Long tvId;
    private Long userId;

}
