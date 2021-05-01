package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.StudyRecruitment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.olivejua.study.domain.board.QStudyRecruitment.studyRecruitment;

public class StudyRecruitmentRepositoryImpl implements StudyRecruitmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StudyRecruitmentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<StudyRecruitment> search() {
        return queryFactory
                .selectFrom(studyRecruitment)
                .fetch();
    }


}
