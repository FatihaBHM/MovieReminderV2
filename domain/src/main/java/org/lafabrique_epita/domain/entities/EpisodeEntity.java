package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEpisode;

    private LocalDate airDate;

    private int episodeNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = false)
    private Long idTmdb;

    private int duration;

    private int seasonNumber;

    private String imagePath;

    @OneToMany
    @JoinColumn(name = "playlist_tv_id")
    private List<PlayListTvEntity> playListTvs;

    @ManyToOne(optional = false)
    @JoinColumn(name = "season_id")
    private SeasonEntity season;

    @ManyToMany
    @JoinTable(name = "episode_comments", joinColumns = @JoinColumn(name = "episode_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
