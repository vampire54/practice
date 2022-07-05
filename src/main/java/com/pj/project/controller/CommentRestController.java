package com.pj.project.controller;

import com.pj.project.dto.CommentRequestDto;
import com.pj.project.dto.SessionUser;
import com.pj.project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final HttpSession session;

    // 작성
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        SessionUser user = (SessionUser) session.getAttribute("user");

        return ResponseEntity.ok(commentService.commentSave(user.getName(), id, dto));
    }

    // 삭제
    @DeleteMapping("/posts/{postsId}/comments/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}