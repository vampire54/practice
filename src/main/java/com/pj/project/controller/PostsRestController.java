package com.pj.project.controller;
import com.pj.project.service.PostsService;
import com.pj.project.dto.PostsRequestDto;
import com.pj.project.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class PostsRestController {

    private final PostsService postsService;

    //글 작성
    @PostMapping("/posts/save")
    public Long save(@RequestBody PostsRequestDto dto) {

        return postsService.save(dto);
    }

    //글 수정
    @PutMapping("/posts/save/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsRequestDto dto) {

        return postsService.update(id, dto);
    }

    //글 읽기
    @GetMapping("/posts/save/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {

        return postsService.findById(id);
    }

    //글 삭제
    @DeleteMapping("/posts/delete/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);

        return id;
    }


}