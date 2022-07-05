package com.pj.project.dto;

import com.pj.project.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;

    private int view;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private List<CommentResponseDto> comments;

    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.writer = posts.getWriter();
        this.view = posts.getView();
        this.createdDate = posts.getCreatedDate();
        this.modifiedDate = posts.getModifiedDate();
        this.comments = posts.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());

    }
}
