package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.CommentService;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long save(@RequestBody CommentSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return commentService.save(requestDto, user.toEntity());
    }

    @PutMapping("/{commentId}")
    public Long update(@PathVariable Long commentId, @RequestBody CommentSaveRequestDto requestDto) {
        return commentService.update(commentId, requestDto.getContent());
    }

    @DeleteMapping("/{commentId}")
    public Long delete(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }
}
