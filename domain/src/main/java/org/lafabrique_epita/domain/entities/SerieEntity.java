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
@Table(name = "serie")
public class SerieEntity extends MasterClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate firstAirDate;

    @Column(nullable = false, unique = true)
    private Long idTmdb;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String posterPath;

    private LocalDate lastAirDate;

    @Column(nullable = false)
    private String title;

    private int numberOfEpisodes;

    private int numberOfSeasons;

    private Float score;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "serie", orphanRemoval = true)
    private List<SeasonEntity> seasons = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "serie_comment", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "serie_genre", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres = new ArrayList<>();
}
