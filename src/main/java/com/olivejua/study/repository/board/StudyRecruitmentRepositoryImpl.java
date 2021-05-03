package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.QTechStack;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.olivejua.study.domain.board.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.board.QTechStack.*;

public class StudyRecruitmentRepositoryImpl implements StudyRecruitmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StudyRecruitmentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<StudyRecruitment> search() {
        return queryFactory
                .selectFrom(studyRecruitment)
                .join(studyRecruitment.techStack, techStack)
                .fetch();
    }

    private BooleanExpression titleEq(String title) {
        return title != null ? studyRecruitment.title.eq(title) : null;
    }

    private BooleanExpression placeEq(String place) {
        return place != null ? studyRecruitment.condition.place.eq(place) : null;
    }

    private BooleanExpression ExplanationEq(String explanation) {
        return explanation != null ? studyRecruitment.condition.explanation.eq(explanation) : null;
    }

    /**
     * techStack을 fetch join으로 가져와야함
     *
     * title
     * techStack (다른 테이블에서 찾아와야함.)
     * place
     * explanation
     */
}
