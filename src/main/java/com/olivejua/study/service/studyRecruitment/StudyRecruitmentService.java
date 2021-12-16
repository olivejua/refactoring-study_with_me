package com.olivejua.study.service.studyRecruitment;

import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.studyRecruitment.NotFoundStudyRecruitmentPost;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.service.user.UserService;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudyRecruitmentService {
    private final StudyRecruitmentRepository studyRecruitmentRepository;
    private final UserService userService;

    public Long savePost(StudyRecruitmentSaveRequestDto requestDto, LoginUser loginUser) {
        User author = userService.findById(loginUser.getId());
        StudyRecruitment savedPost = studyRecruitmentRepository.save(requestDto.toEntity(author));

        return savedPost.getId();
    }

    public void updatePost(Long postId, StudyRecruitmentUpdateRequestDto requestDto, LoginUser loginUser) {
        StudyRecruitment post = findPostById(postId);
        post.hasSameAuthorAs(findUserById(loginUser.getId()));
        post.update(requestDto.getTitle(), requestDto.toCondition(), requestDto.getTechs());
    }

    public void deletePost(Long postId, LoginUser loginUser) {
        StudyRecruitment post = findPostById(postId);
        post.hasSameAuthorAs(findUserById(loginUser.getId()));

        studyRecruitmentRepository.delete(post);
    }

    public StudyRecruitment findPostById(Long postId) {
        return studyRecruitmentRepository.findById(postId)
                .orElseThrow(() -> new NotFoundStudyRecruitmentPost(postId));
    }

    public User findUserById(Long userId) {
        return userService.findById(userId);
    }

    public StudyRecruitmentResponseDto getOnePost(Long postId) {
        StudyRecruitment findPost = findPostById(postId);
        findPost.addViewCount();
        return new StudyRecruitmentResponseDto(findPost);
    }
}

