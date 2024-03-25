package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lafabrique_epita.domain.enums.StatusEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "playlist_movie")
public class PlayListMovieEntity {

    @EmbeddedId
    private PlayListMovieID id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private float score;

    private boolean favorite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
