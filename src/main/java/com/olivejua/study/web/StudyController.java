package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.StudyService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/study")
@RestController
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/{postId}")
    public PostReadResponseDto read(@PathVariable Long postId, PageDto pageInfo) {
        PostReadResponseDto responseDto = studyService.read(postId);
        responseDto.savePageInfo(pageInfo);

        return responseDto;
    }

    @PostMapping
    public Long save(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return studyService.post(requestDto, user.toEntity());
    }

    @PutMapping("/{postId}")
    public Long update(@PathVariable Long postId, @RequestBody PostSaveRequestDto requestDto) {
        return studyService.update(postId, requestDto);
    }

    @DeleteMapping("/{postId}")
    public Long delete(@PathVariable Long postId) {
        return studyService.delete(postId);
    }
}
