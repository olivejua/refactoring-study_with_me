package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.StudyService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/study")
@RestController
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadResponseDto> read(@PathVariable Long postId, PageDto pageInfo, HttpServletRequest httpServletRequest) {
        PostReadResponseDto responseDto = studyService.read(
                postId, httpServletRequest.getSession().getServletContext().getRealPath("/"));
        responseDto.savePageInfo(pageInfo);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedPostId = studyService.post(requestDto, user.toEntity());
        return ResponseEntity.created(URI.create("/study/"+savedPostId)).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId, @RequestBody PostSaveRequestDto requestDto) {
        studyService.update(postId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        studyService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
