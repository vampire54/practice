package com.pj.project.dto;

import com.pj.project.domain.posts.Posts;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsRequestDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String createdDate;
    private String modifiedDate;
    private int view;


    //Dto -> entity//
    public Posts toEntity() {
        Posts posts = Posts.builder()
                .id(id)
                .title(title)
                .writer(writer)
                .content(content)
                .view(0)
                .build();

        return posts;
    }
}
