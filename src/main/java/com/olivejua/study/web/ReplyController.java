package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.ReplyService;
import com.olivejua.study.utils.UrlPaths;
import com.olivejua.study.web.dto.reply.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(UrlPaths.REPLIES)
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ReplySaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedReplyId = replyService.save(requestDto, user.toEntity());
        return ResponseEntity.created(URI.create("/reply/"+savedReplyId)).build();
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<Void> update(@PathVariable Long replyId, @RequestBody ReplySaveRequestDto requestDto) {
        replyService.update(replyId, requestDto.getContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> delete(@PathVariable Long replyId) {
        replyService.delete(replyId);
        return ResponseEntity.noContent().build();
    }
}
