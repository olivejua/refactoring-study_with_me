package com.olivejua.study.service.studyRecruitment;

import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.studyRecruitment.NotFoundStudyRecruitmentPost;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.response.PageInfo;
import com.olivejua.study.service.user.UserService;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentReadResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public StudyRecruitmentReadResponseDto getOnePost(Long postId) {
        StudyRecruitment findPost = findPostById(postId);
        findPost.addViewCount();
        return new StudyRecruitmentReadResponseDto(findPost);
    }

    public PostListResponseDto<StudyRecruitmentListResponseDto> getPosts(Pageable pageable) {
        Page<StudyRecruitment> posts = studyRecruitmentRepository.findPosts(pageable);
        List<StudyRecruitmentListResponseDto> listResponseDtos = mapToStudyRecruitmentListResponseDto(posts.getContent());

        PageInfo pageInfo = toPageInfo(posts);
        return new PostListResponseDto<>(listResponseDtos, pageInfo);
    }

    private PageInfo toPageInfo(Page<StudyRecruitment> posts) {
        return PageInfo.builder()
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(posts.getNumber())
                .first(posts.isFirst())
                .last(posts.isLast())
                .numberOfElements(posts.getNumberOfElements())
                .build();
    }

    private List<StudyRecruitmentListResponseDto> mapToStudyRecruitmentListResponseDto(List<StudyRecruitment> entities) {
        return entities.stream()
                .map(StudyRecruitmentListResponseDto::new)
                .collect(Collectors.toList());
    }
}

