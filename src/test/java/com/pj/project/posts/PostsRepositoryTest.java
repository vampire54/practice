package com.pj.project.posts;

import com.pj.project.domain.posts.Posts;
import com.pj.project.repository.PostsRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Log4j2
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
    public void reset() {
        postsRepository.deleteAll();
    }

    @AfterEach
    public void clear() {
        postsRepository.deleteAll();
    }

    @Test
    public void 글저장_불러오기() {
        //g
        String title = "test title";
        String content = "test content";

        postsRepository.save(Posts.builder()
                .title(title).
                content(content).
                writer("test").
                build());
        //w
        List<Posts> postsList = postsRepository.findAll();

        //t
        Posts posts = postsList.get(0);

        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

        log.info(posts);
    }
}