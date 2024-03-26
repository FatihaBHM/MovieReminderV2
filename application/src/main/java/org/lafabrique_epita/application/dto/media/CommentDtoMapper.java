package org.lafabrique_epita.application.dto.media;

import org.lafabrique_epita.domain.entities.CommentEntity;

public class CommentDtoMapper {

    private CommentDtoMapper() {
    }

    public static CommentDto convertToDto(CommentEntity commentEntity) {
        return new CommentDto(
                commentEntity.getId(),
                commentEntity.getDescription(),
                commentEntity.getScore()
        );
    }
}
