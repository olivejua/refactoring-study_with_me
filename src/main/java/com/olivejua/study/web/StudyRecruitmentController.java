package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.olivejua.study.utils.ApiUrlPaths.*;

@RequestMapping(STUDY_RECRUITMENT)
@RestController
public class StudyRecruitmentController {

    @GetMapping(POSTS)
    public ResponseEntity<Long> getPosts() {
        return ResponseEntity.ok(123L);
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<Long> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postId);
    }

    @PostMapping(POSTS)
    public ResponseEntity<String> savePost(@AppLoginUser LoginUser loginUser) {
        assert loginUser != null;
        return ResponseEntity.ok("Success!");
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
