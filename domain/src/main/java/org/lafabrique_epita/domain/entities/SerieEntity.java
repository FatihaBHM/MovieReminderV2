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
public class SerieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate firstAirDate;

    @Column(nullable = false)
    private Long idTmdb;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    private String posterPath;

   // @Column(nullable = true)
    //private String image_landscape;

    //@Column(nullable = true)
   // private String image_portrait;

    private LocalDate lastAirDate;

    @Column(nullable = false)
    private String title;

    private int numberOfEpisodes;

    private int numberOfSeasons;

    private float score;

    @OneToMany
    @JoinColumn(name = "favorite_id")
    private List<FavoriteEntity> favorites;

    @ManyToMany
    @JoinTable(name = "serie_comments", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments;

    @ManyToMany
    @JoinTable(name = "serie_genres", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres;

    @ManyToMany
    @JoinTable(name = "serie_languages", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<LanguageEntity> languages;

    @ManyToMany
    @JoinTable(name = "serie_countries", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<CountryEntity> countries;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
