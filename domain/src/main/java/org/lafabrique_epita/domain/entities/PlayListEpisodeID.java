package org.lafabrique_epita.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PlayListEpisodeID implements Serializable {

    @Column(name = "episode_id")
    private Long episodeId;

    @Column(name = "user_id")
    private Long userId;

}
