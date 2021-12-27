package com.olivejua.study.service.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.post.NotFoundPostException;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.service.post.PostService;
import com.olivejua.study.web.dto.post.PostListResponseDtos;
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

import static com.olivejua.study.utils.PostImagePaths.STUDY_RECRUITMENT;

@RequiredArgsConstructor
@Transactional
@Service
public class DefaultStudyRecruitmentService implements StudyRecruitmentService {
    private final StudyRecruitmentRepository studyRecruitmentRepository;
    private final PostService postService;

    @Override
    public Long savePost(StudyRecruitmentSaveRequestDto requestDto, User author) {
        StudyRecruitment savedPost = studyRecruitmentRepository.save(requestDto.toEntity(author));

        List<String> uploadedImageUrls = postService.uploadImages(requestDto.getImages(), STUDY_RECRUITMENT, savedPost.getId());
        savedPost.addImages(uploadedImageUrls);

        return savedPost.getId();
    }

    @Override
    public void updatePost(Long postId, StudyRecruitmentUpdateRequestDto requestDto, User author) {
        StudyRecruitment post = findPostById(postId);
        postService.validateAuthor(post, author);

        post.update(requestDto.getTitle(), requestDto.toCondition(), requestDto.getTechs());

        List<String> uploadedImageUrls = postService.replaceImages(requestDto.getImages(), STUDY_RECRUITMENT, post);
        post.replaceImages(uploadedImageUrls);
    }

    @Override
    public void deletePost(Long postId, User author) {
        StudyRecruitment post = findPostById(postId);
        postService.validateAuthor(post, author);
        postService.removeImages(post);

        studyRecruitmentRepository.delete(post);
    }

    @Override
    public StudyRecruitmentReadResponseDto getOnePost(Long postId) {
        StudyRecruitment findPost = findPostById(postId);
        findPost.addViewCount();
        return new StudyRecruitmentReadResponseDto(findPost);
    }

    @Override
    public PostListResponseDtos<StudyRecruitmentListResponseDto> getPosts(Pageable pageable) {
        Page<StudyRecruitment> posts = studyRecruitmentRepository.findPosts(pageable);
        List<StudyRecruitmentListResponseDto> listResponseDtos = mapToStudyRecruitmentListResponseDto(posts.getContent());

        return new PostListResponseDtos<>(listResponseDtos, postService.toPageInfo(posts));
    }

    private StudyRecruitment findPostById(Long postId) {
        return studyRecruitmentRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));
    }

    private List<StudyRecruitmentListResponseDto> mapToStudyRecruitmentListResponseDto(List<StudyRecruitment> entities) {
        return entities.stream()
                .map(StudyRecruitmentListResponseDto::new)
                .collect(Collectors.toList());
    }
}

