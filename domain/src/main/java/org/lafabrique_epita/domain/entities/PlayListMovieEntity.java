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
@Table(name = "playlist_movie")
public class PlayListMovieEntity extends MasterClass {

    @EmbeddedId
    private PlayListMovieID id;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Float score;

    private boolean favorite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
