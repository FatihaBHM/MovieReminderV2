package org.lafabrique_epita.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String backdropPath;

    @Column(nullable = false)
    private Long idTmdb;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    private float score;

    private LocalDate releaseDate;

    private int duration;

    @Column(nullable = false)
    private String title;

    @OneToMany
    @JoinColumn(name = "playlist_movie_id")
    private List<PlayListMovieEntity> playListMovies = List.of();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_comment", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments = List.of();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres = List.of();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
