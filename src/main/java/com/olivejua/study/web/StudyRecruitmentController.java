package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.response.SuccessResult;
import com.olivejua.study.service.user.UserService;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.olivejua.study.utils.ApiUrlPaths.*;

@RequiredArgsConstructor
@RequestMapping(STUDY_RECRUITMENT)
@RestController
public class StudyRecruitmentController {
    private final UserService userService;
    private final StudyRecruitmentRepository studyRecruitmentRepository;

    @GetMapping(POSTS)
    public ResponseEntity<Long> getPosts() {
        return ResponseEntity.ok(123L);
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<Long> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postId);
    }

    @PostMapping(POSTS)
    public ResponseEntity<SuccessResult> savePost(@NotNull @AppLoginUser LoginUser loginUser,
                                                  @Validated @RequestBody StudyRecruitmentSaveRequestDto requestDto) {
        User author = userService.findById(loginUser.getId());
        StudyRecruitment savedPost = studyRecruitmentRepository.save(requestDto.toEntity(author));

        return ResponseEntity.created(URI.create(STUDY_RECRUITMENT+POSTS+"/"+savedPost.getId()))
                .body(SuccessResult.createSuccessResult());
    }

    @PutMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<String> updatePost(@PathVariable Long postId) {
        return ResponseEntity.ok("Success! postId=" + postId);
    }

    @DeleteMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok("Success! postId=" + postId);
    }
}
