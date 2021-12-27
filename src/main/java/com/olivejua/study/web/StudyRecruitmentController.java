package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.exception.validation.RequestDtoValidationException;
import com.olivejua.study.response.ListResult;
import com.olivejua.study.response.SingleResult;
import com.olivejua.study.response.SuccessResult;
import com.olivejua.study.service.studyRecruitment.StudyRecruitmentService;
import com.olivejua.study.web.dto.post.PostListResponseDtos;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentReadResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<ListResult<StudyRecruitmentListResponseDto>> getPosts(Pageable pageRequest) {
        PostListResponseDtos<StudyRecruitmentListResponseDto> findPosts = studyRecruitmentService.getPosts(pageRequest);
        return ResponseEntity.ok(findPosts.toListResult());
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SingleResult> getPost(@PathVariable Long postId) {
        StudyRecruitmentReadResponseDto findPost = studyRecruitmentService.getOnePost(postId);
        SingleResult result = SingleResult.createSuccessResult(findPost);
        return ResponseEntity.ok(result);
    }

    @PostMapping(POSTS)
    public ResponseEntity<SuccessResult> savePost(@AppLoginUser LoginUser loginUser,
                                                  @Validated StudyRecruitmentSaveRequestDto requestDto, Errors errors) {

        validateErrors(errors);

        Long savedPostId = studyRecruitmentService.savePost(requestDto, loginUser.getUser());

        return ResponseEntity.created(URI.create(STUDY_RECRUITMENT+POSTS+"/"+savedPostId))
                .body(SuccessResult.createSuccessResult());
    }

    @PutMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> updatePost(@PathVariable Long postId,
                                                    @Validated StudyRecruitmentUpdateRequestDto requestDto, Errors errors,
                                                    @AppLoginUser LoginUser loginUser) {
        validateErrors(errors);

        studyRecruitmentService.updatePost(postId, requestDto, loginUser.getUser());
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }

    @DeleteMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> deletePost(@PathVariable Long postId,
                                                    @AppLoginUser LoginUser loginUser) {

        studyRecruitmentService.deletePost(postId, loginUser.getUser());
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }

    private void validateErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new RequestDtoValidationException(errors);
        }
    }
}
