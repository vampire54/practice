package com.pj.project.controller;

import com.pj.project.domain.posts.Posts;
import com.pj.project.dto.CommentResponseDto;
import com.pj.project.service.PostsService;
import com.pj.project.dto.PostsResponseDto;
import com.pj.project.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor    //생성자 final or @NotNull의 필드 자동생성
@Controller
public class PostsController { //화면 전환 용

    private final PostsService postsService;
    private final HttpSession session;

    //게시판 글 리스트 페이지
    @GetMapping("/posts/list")
    public String list(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                       Pageable pageable){

        SessionUser user = (SessionUser) session.getAttribute("user");
        Page<Posts> list = postsService.pageList(pageable);

        /*페이징*/
        model.addAttribute("page", postsService.pageList(pageable));
        int nowPage = list.getPageable().getPageNumber()+1; //pageable이 갖고 있는 페이지는 0부터 시작하기 때문에
        int startPage = Math.max(nowPage -4,1);
        int endPage = Math.min(nowPage +5,list.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        if(user != null){
            model.addAttribute("user", user.getName());
        }

        return "posts/list";
    }
    //게시판 글 작성 페이지
    @GetMapping("/posts/save")
    public String write(Model model){
        SessionUser user = (SessionUser) session.getAttribute("user");

        if(user != null){
            model.addAttribute("user", user.getName());
        }

        return "posts/write";
    }
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, Model model){
        SessionUser user = (SessionUser) session.getAttribute("user");

        PostsResponseDto dto = postsService.findById(id);
        List<CommentResponseDto> comments = dto.getComments();
        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user.getName());

        }
        postsService.updateView(id);
        model.addAttribute("posts", dto);


        return "posts/read";
    }

    //게시판 글 수정 페이지
    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts/update";
    }
    //검색
    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable){

        SessionUser user = (SessionUser) session.getAttribute("user");
        Page<Posts> searchList = postsService.search(keyword, pageable);
        model.addAttribute("searchList", searchList);
        //페이징
        int nowPage = searchList.getPageable().getPageNumber()+1; //pageable이 갖고 있는 페이지는 0부터 시작하기 때문에
        int startPage = Math.max(nowPage -4,1);
        int endPage = Math.min(nowPage +5,searchList.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        if(user != null){
            model.addAttribute("user", user.getName());
        }
        return "posts/search";
    }
}
