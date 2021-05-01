package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.StudyRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRecruitmentRepository extends JpaRepository<StudyRecruitment, Long>,
                                                StudyRecruitmentRepositoryCustom {
}
