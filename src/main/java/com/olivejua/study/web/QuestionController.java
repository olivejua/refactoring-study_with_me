package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.QuestionService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public ResponseEntity<Page<PostListResponseDto>> list(@PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable,
                                                          SearchDto searchInfo) {
        Page<PostListResponseDto> results = searchInfo == null ?
                                                questionService.list(pageable) :
                                                questionService.search(searchInfo, pageable);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadResponseDto> read(@PathVariable Long postId, PageDto pageInfo, HttpServletRequest request) {
        PostReadResponseDto responseDto =
                questionService.read(postId, request.getSession().getServletContext().getRealPath("/"));
        responseDto.savePageInfo(pageInfo);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedPostId = questionService.post(requestDto, user.toEntity());
        return ResponseEntity.created(URI.create("/question/"+savedPostId)).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        questionService.update(postId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        questionService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
