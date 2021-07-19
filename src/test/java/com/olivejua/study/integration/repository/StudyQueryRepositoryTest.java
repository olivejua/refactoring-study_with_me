package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.board.StudyRecruitmentQueryRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class StudyQueryRepositoryTest extends CommonBoardRepositoryTest {

    /**
     * Dependency Injection
     */
    @Autowired
    private StudyRecruitmentRepository studyRepository;

    @Autowired
    private StudyRecruitmentQueryRepository studyQueryRepository;

    @Autowired
    private TechStackRepository techStackRepository;


    /**
     * test template zone
     */
    @Override
    void setup() {

    }

    @Override
    void clearAll() {

    }


    /**
     * overriding zone
     */
    @Override
    Board createDummyPost() {
        StudyRecruitment post = StudyRecruitment.createPost(
                dummyWriter,
                "sample title",
                List.of("java", "spring", "jpa"),
                Condition.createCondition(
                        "sample place",
                        LocalDateTime.of(2021, 07, 19, 00, 00),
                        LocalDateTime.of(2021, 12, 19, 00, 00),
                        10,
                        "sample explanation"));

        post.getTechStack().forEach(techStackRepository::save);
        return studyRepository.save(post);
    }
}
