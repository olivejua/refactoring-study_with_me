package com.olivejua.study.repository.query;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyRecruitmentQueryRepository {
    Page<StudyRecruitment> findPosts(Pageable pageable);
}
