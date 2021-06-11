package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.CommentService;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedCommentId = commentService.save(requestDto, user.toEntity());
        return ResponseEntity.created(URI.create("/comment/"+savedCommentId)).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(@PathVariable Long commentId, @RequestBody CommentSaveRequestDto requestDto) {
        commentService.update(commentId, requestDto.getContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
