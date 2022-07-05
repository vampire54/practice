package com.pj.project.web;

import com.pj.project.dto.PostsRequestDto;
import com.pj.project.domain.posts.Posts;
import com.pj.project.repository.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @Autowired
    private PostsRepository postsRepository;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @After
    public void clear() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_등록_테스트() throws Exception {
        //g
        String title ="title";
        String content ="content";

        PostsRequestDto dto = PostsRequestDto.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build();


        String url = "http://localhost:" + port + "/posts/save/";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void 게시글_수정_테스트() {

        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .writer("writer")
                .content("content")
                .build());

        Long updateId = savePosts.getId();
        String changeTitle = "title2";
        String changeContent = "content2";

        PostsRequestDto requestDto = PostsRequestDto.builder()
                .title(changeTitle)
                .content(changeContent)
                .build();

        String url = "http://localhost:" + port + "/posts/save/" + updateId;

        HttpEntity<PostsRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(changeTitle);
        assertThat(all.get(0).getContent()).isEqualTo(changeContent);
    }



}