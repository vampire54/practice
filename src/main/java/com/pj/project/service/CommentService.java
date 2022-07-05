package com.pj.project.service;

import com.pj.project.domain.User.User;
import com.pj.project.domain.posts.Comment;
import com.pj.project.domain.posts.Posts;
import com.pj.project.dto.CommentRequestDto;
import com.pj.project.repository.CommentRepository;
import com.pj.project.repository.PostsRepository;
import com.pj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    /* CREATE */
    @Transactional
    public Long commentSave(String name, Long id, CommentRequestDto dto) {
        Optional<User> user = userRepository.findByName(name);
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

        dto.setUser(user.get());
        dto.setPosts(posts);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getId();
    }


    // 삭제
    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }
}