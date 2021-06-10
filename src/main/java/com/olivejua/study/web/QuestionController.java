package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.QuestionService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{postId}")
    public PostReadResponseDto read(@PathVariable Long postId, PageDto pageInfo) {
        PostReadResponseDto responseDto = questionService.read(postId);
        responseDto.savePageInfo(pageInfo);

        return responseDto;
    }

    @PostMapping
    public Long post(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return questionService.post(requestDto, user.toEntity());
    }

    @PutMapping("/{postId}")
    public Long update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        return questionService.update(postId, requestDto);
    }

    @DeleteMapping("/{postId}")
    public Long delete(@PathVariable Long postId) {
        return questionService.delete(postId);
    }
}
