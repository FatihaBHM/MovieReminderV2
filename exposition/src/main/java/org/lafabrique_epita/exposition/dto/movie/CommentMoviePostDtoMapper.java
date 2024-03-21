package org.lafabrique_epita.exposition.dto.movie;

import lombok.RequiredArgsConstructor;
import org.lafabrique_epita.domain.entities.CommentEntity;
import org.lafabrique_epita.exposition.dto.movie.CommentMoviePostDto;

@RequiredArgsConstructor
public class CommentMoviePostDtoMapper {

    public static CommentEntity convertToCommentEntity(CommentMoviePostDto commentMoviePostDto) {
        CommentEntity comment = new CommentEntity();
        comment.setDescription(commentMoviePostDto.comment());
        comment.setScore(commentMoviePostDto.score());
        return comment;
    }
}
