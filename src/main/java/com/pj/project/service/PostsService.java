package com.pj.project.service;

import com.pj.project.web.dto.PostsRequestDto;
import com.pj.project.web.dto.PostsResponseDto;
import com.pj.project.domain.posts.Posts;
import com.pj.project.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor/*생성자 생략*/
@Service
public class PostsService {

    private final PostsRepository repository;
    // 글 작성
    @Transactional
    public Long save(PostsRequestDto dto){
        Posts posts = dto.toEntity();
        repository.save(posts);

        return posts.getId();
    }

    /* 글수정 (dirty checking 영속성 컨텍스트(엔티티를 영구적으로 저장))
     * 트랜잭션 안에서 데이터를 가져오면 영속성 컨텍스트 유지가 된 상태
     * 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블 변경을 반영
     * 즉, update 쿼리 필요x
     */
    @Transactional
    public Long update(Long id, PostsRequestDto dto) {
        Posts posts = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        posts.update(dto.getTitle(), dto.getContent());

        return id;
    }

    //게시판 글 읽기
    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts posts = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new PostsResponseDto(posts);
    }

    //게시판 글 리스트 (데이터 불러오기)
    @Transactional
    public List<PostsResponseDto> findAllDesc(){

        return repository.findAllDesc().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id) {
        Posts posts = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        repository.delete(posts);
    }
}
