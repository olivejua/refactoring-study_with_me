package com.olivejua.study.service.studyRecruitment;

import com.olivejua.study.domain.user.User;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentListResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentReadResponseDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import org.springframework.data.domain.Pageable;

public interface StudyRecruitmentService {

    Long savePost(StudyRecruitmentSaveRequestDto requestDto, User author);

    void updatePost(Long postId, StudyRecruitmentUpdateRequestDto requestDto, User author);

    void deletePost(Long postId, User author);

    StudyRecruitmentReadResponseDto getOnePost(Long postId);

    PostListResponseDto<StudyRecruitmentListResponseDto> getPosts(Pageable pageable);
}
