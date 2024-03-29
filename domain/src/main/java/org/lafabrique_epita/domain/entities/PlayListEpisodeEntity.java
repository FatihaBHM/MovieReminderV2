package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lafabrique_epita.domain.enums.StatusEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlist_episode")
public class PlayListEpisodeEntity extends MasterClass {

    @EmbeddedId
    private PlayListEpisodeID id;

    @ManyToOne(optional = false)
    @MapsId("episodeId")
    @JoinColumn(name = "episode_id")
    private EpisodeEntity episode;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int score;

    private boolean favorite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
