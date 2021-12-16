package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.response.ListResult;
import com.olivejua.study.response.SingleResult;
import com.olivejua.study.response.SuccessResult;
import com.olivejua.study.service.studyRecruitment.StudyRecruitmentService;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import com.olivejua.study.web.dto.post.PostReadResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentReadResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ListResult<StudyRecruitmentListResponseDto>> getPosts(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageRequest) {
        PostListResponseDto<StudyRecruitmentListResponseDto> findPosts = studyRecruitmentService.getPosts(pageRequest);
        ListResult<StudyRecruitmentListResponseDto> result = new ListResult<>(findPosts.getPosts(), findPosts.getPageInfo());
        return ResponseEntity.ok(result);
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SingleResult> getPost(@PathVariable Long postId) {
        StudyRecruitmentReadResponseDto findPost = studyRecruitmentService.getOnePost(postId);
        PostReadResponseDto<StudyRecruitmentReadResponseDto> responseDto = new PostReadResponseDto<>(findPost);
        SingleResult result = SingleResult.createSuccessResult(responseDto);
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
