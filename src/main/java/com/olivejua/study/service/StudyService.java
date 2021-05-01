package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StudyService {
    private final StudyRecruitmentRepository studyRepository;
    private final TechStackService techStackService;

    public Long post(PostSaveRequestDto requestDto, User writer) {
        StudyRecruitment newPost =
                StudyRecruitment.savePost(writer, requestDto.getTitle(),
                        requestDto.getTechStack(), requestDto.getCondition());

        studyRepository.save(newPost);
        techStackService.update(newPost);

        return newPost.getId();
    }

    public Long update(Long postId, PostSaveRequestDto requestDto) {
        StudyRecruitment post = findPost(postId);

        post.edit(requestDto.getTitle(), requestDto.getCondition(), requestDto.getTechStack());
        techStackService.update(post);

        return post.getId();
    }

    public void delete(Long postId) {
        StudyRecruitment post = findPost(postId);

        techStackService.delete(post);
        studyRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public PostReadResponseDto read(Long postId) {
        StudyRecruitment post = findPost(postId);

        return new PostReadResponseDto(post);
    }

    private StudyRecruitment findPost(Long postId) throws IllegalArgumentException {
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
        delete(post);
        post.getTechStack().forEach(techStackRepository::save);
    }

    public void delete(StudyRecruitment post) {
        techStackRepository.deleteByPost(post);
    }
}