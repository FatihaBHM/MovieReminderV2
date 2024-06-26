package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "season")
public class SeasonEntity extends MasterClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate airDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = false, unique = true)
    private Long idTmdb;

    private String posterPath;

    private Integer seasonNumber;


    @ManyToOne(optional = false)
    @JoinColumn(name = "serie_id")
    private SerieEntity serie;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season")
    private List<EpisodeEntity> episodes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "season_comment", joinColumns = @JoinColumn(name = "season_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = new ArrayList<>();
}
