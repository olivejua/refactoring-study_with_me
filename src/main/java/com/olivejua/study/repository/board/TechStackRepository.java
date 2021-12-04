package com.olivejua.study.repository.board;

import com.olivejua.study.domain.StudyRecruitment;
import com.olivejua.study.domain.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<Tech, Long> {
    void deleteByPost(StudyRecruitment post);
}
