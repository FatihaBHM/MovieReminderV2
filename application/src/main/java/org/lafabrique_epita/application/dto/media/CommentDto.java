package org.lafabrique_epita.application.dto.media;


import jakarta.validation.constraints.Size;

// Getters
// Pas les setters
// toString
// equals
// hashCode
// constructeur avec tous les paramètres
// Elle est final (pas d'héritage sur ce record)
public record CommentDto(
        Long id,

        @Size(min = 10, max = 1000, message = "La description doit être comprise entre 10 et 1000 caractères")
        String description,

        float score
) {}
