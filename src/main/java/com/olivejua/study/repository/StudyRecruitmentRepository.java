package com.olivejua.study.repository;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.repository.query.StudyRecruitmentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRecruitmentRepository extends JpaRepository<StudyRecruitment, Long>, StudyRecruitmentQueryRepository {
}
