package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
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
        techStackService.save(newPost);

        return newPost.getId();
    }
}

@RequiredArgsConstructor
@Transactional
@Service
class TechStackService {
    private final TechStackRepository techStackRepository;

    public void save(StudyRecruitment post) {
        techStackRepository.deleteByPost(post);
        post.getTechStack().forEach(techStackRepository::save);
    }
}