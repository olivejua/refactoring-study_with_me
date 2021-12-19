package com.olivejua.study.service.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.post.DifferentUserWithPostAuthorException;
import com.olivejua.study.exception.post.NotFoundPostException;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.response.PageInfo;
import com.olivejua.study.service.upload.UploadService;
import com.olivejua.study.utils.PostImagePaths;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class DefaultStudyRecruitmentService implements StudyRecruitmentService {
    private final StudyRecruitmentRepository studyRecruitmentRepository;
    private final UploadService uploadService;

    @Override
    public Long savePost(StudyRecruitmentSaveRequestDto requestDto, User author) {
        StudyRecruitment savedPost = studyRecruitmentRepository.save(requestDto.toEntity(author));

        List<String> uploadedImageUrls = uploadImages(requestDto.getImages(), savedPost.getId());
        savedPost.addImages(uploadedImageUrls);

        return savedPost.getId();
    }

    @Override
    public void updatePost(Long postId, StudyRecruitmentUpdateRequestDto requestDto, User author) {
        StudyRecruitment post = findPostById(postId);
        validateAuthor(post, author);

        post.update(requestDto.getTitle(), requestDto.toCondition(), requestDto.getTechs());

        List<String> uploadedImageUrls = replaceImages(requestDto.getImages(), post);
        post.replaceImages(uploadedImageUrls);
    }

    @Override
    public void deletePost(Long postId, User author) {
        StudyRecruitment post = findPostById(postId);
        validateAuthor(post, author);

        removeImages(post);

        studyRecruitmentRepository.delete(post);
    }

    private void validateAuthor(StudyRecruitment post, User author) {
        if (post.hasSameAuthorAs(author)) {
            return;
        }

        throw new DifferentUserWithPostAuthorException();
    }

    private List<String> uploadImages(List<MultipartFile> images, Long postId) {
        if (images.isEmpty()) return Collections.emptyList();

        return uploadService.upload(images, PostImagePaths.STUDY_RECRUITMENT + postId);
    }

    private List<String> replaceImages(List<MultipartFile> images, StudyRecruitment post) {
        removeImages(post);
        return uploadImages(images, post.getId());
    }

    private void removeImages(StudyRecruitment post) {
        if (post.hasImages()) {
            uploadService.remove(post.getImagePaths());
        }
    }

    private StudyRecruitment findPostById(Long postId) {
        return studyRecruitmentRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));
    }

    @Override
    public StudyRecruitmentReadResponseDto getOnePost(Long postId) {
        StudyRecruitment findPost = findPostById(postId);
        findPost.addViewCount();
        return new StudyRecruitmentReadResponseDto(findPost);
    }

    @Override
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

