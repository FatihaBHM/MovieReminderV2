package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "episode")
public class EpisodeEntity extends MasterClass{

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "season_id")
    private SeasonEntity season;

    @ManyToMany
    @JoinTable(name = "episode_comment", joinColumns = @JoinColumn(name = "episode_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = new ArrayList<>();
}
