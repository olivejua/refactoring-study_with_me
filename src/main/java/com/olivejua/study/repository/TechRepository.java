package com.olivejua.study.repository;

import com.olivejua.study.domain.StudyRecruitment;
import com.olivejua.study.domain.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechRepository extends JpaRepository<Tech, Long> {
    void deleteByPost(StudyRecruitment post);
}
