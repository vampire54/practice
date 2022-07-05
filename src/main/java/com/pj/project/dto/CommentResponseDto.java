package com.pj.project.dto;

import com.pj.project.domain.User.User;
import com.pj.project.domain.posts.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String createdDate;
    private String modifiedDate;
    private Long postsId;

    private User user;

    /* Entity -> Dto*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.postsId = comment.getPosts().getId();
        this.user = comment.getUser();
    }
}