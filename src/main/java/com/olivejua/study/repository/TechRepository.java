package com.olivejua.study.repository;

import com.olivejua.study.domain.StudyRecruitment;
import com.olivejua.study.domain.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechRepository extends JpaRepository<Tech, Long> {
    List<Tech> findByPost(StudyRecruitment post);

    void deleteByPost(StudyRecruitment post);
}
