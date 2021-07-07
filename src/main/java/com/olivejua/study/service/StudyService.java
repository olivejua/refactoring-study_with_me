package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.board.StudyRecruitmentQueryRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import com.olivejua.study.utils.BoardImageUploader;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudyService {
    private final StudyRecruitmentRepository studyRepository;
    private final StudyRecruitmentQueryRepository studyQueryRepository;
    private final TechStackService techStackService;
    private final CommentService commentService;
    private final BoardImageUploader boardImageUploader;

    public Page<PostListResponseDto> list(Pageable pageable) {
        return studyQueryRepository.findEntities(pageable);
    }

    public Page<PostListResponseDto> search(SearchDto searchDto, Pageable pageable) {
        return studyQueryRepository.findEntitiesWith(searchDto, pageable);
    }

    public Long post(PostSaveRequestDto requestDto, User writer) {
        StudyRecruitment newPost =
                StudyRecruitment.createPost(writer, requestDto.getTitle(),
                        requestDto.getTechStack(), requestDto.getCondition());

        studyRepository.save(newPost);
        techStackService.update(newPost);
        boardImageUploader.uploadImagesInStudy(newPost);

        return newPost.getId();
    }

    public Long update(Long postId, PostSaveRequestDto requestDto) {
        StudyRecruitment post = findPost(postId);
        
        post.update(requestDto.getTitle(), requestDto.getCondition(), requestDto.getTechStack());
        techStackService.update(post);
        boardImageUploader.updateImagesInStudy(post);

        return post.getId();
    }

    public Long delete(Long postId) {
        StudyRecruitment post = findPost(postId);

        commentService.deleteByPost(post);
        techStackService.deleteByPost(post);
        studyRepository.delete(post);
        boardImageUploader.deleteImagesInStudy(postId);

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostReadResponseDto read(Long postId, String servletPath) {
        StudyRecruitment entity = studyQueryRepository.findEntity(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        boardImageUploader.readImagesInStudy(entity, servletPath);

        return new PostReadResponseDto(entity);
    }

    private StudyRecruitment findPost(Long postId) {
        return studyRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
    }
}

@RequiredArgsConstructor
@Transactional
@Service
class TechStackService {
    private final TechStackRepository techStackRepository;

    public void update(StudyRecruitment post) {
        deleteByPost(post);
        post.getTechStack().forEach(techStackRepository::save);
    }

    public void deleteByPost(StudyRecruitment post) {
        techStackRepository.deleteByPost(post);
    }
}