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
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate airDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = false)
    private int idTmdb;

    private String poster_path;

    private int seasonNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "serie_id")
    private SerieEntity serie;

    @ManyToMany
    @JoinTable(name = "season_comments", joinColumns = @JoinColumn(name = "season_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
