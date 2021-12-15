package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.response.SingleResult;
import com.olivejua.study.response.SuccessResult;
import com.olivejua.study.service.studyRecruitment.StudyRecruitmentService;
import com.olivejua.study.web.dto.post.PostResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.olivejua.study.utils.ApiUrlPaths.*;

@RequiredArgsConstructor
@RequestMapping(STUDY_RECRUITMENT)
@RestController
public class StudyRecruitmentController {
    private final StudyRecruitmentService studyRecruitmentService;

    @GetMapping(POSTS)
    public ResponseEntity<Long> getPosts() {
        return ResponseEntity.ok(123L);
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SingleResult<PostResponseDto<StudyRecruitmentResponseDto>>> getPost(@PathVariable Long postId) {


        SingleResult<PostResponseDto> result = new SingleResult<>();
        return ResponseEntity.ok(result);
    }

    @PostMapping(POSTS)
    public ResponseEntity<SuccessResult> savePost(@AppLoginUser LoginUser loginUser,
                                                  @Validated @RequestBody StudyRecruitmentSaveRequestDto requestDto) {

        Long savedPostId = studyRecruitmentService.savePost(requestDto, loginUser);

        return ResponseEntity.created(URI.create(STUDY_RECRUITMENT+POSTS+"/"+savedPostId))
                .body(SuccessResult.createSuccessResult());
    }

    @PutMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> updatePost(@PathVariable Long postId,
                                                    @Validated @RequestBody StudyRecruitmentUpdateRequestDto requestDto,
                                                    @AppLoginUser LoginUser loginUser) {

        studyRecruitmentService.updatePost(postId, requestDto, loginUser);
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }

    @DeleteMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> deletePost(@PathVariable Long postId,
                                                    @AppLoginUser LoginUser loginUser) {

        studyRecruitmentService.deletePost(postId, loginUser);
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }
}
