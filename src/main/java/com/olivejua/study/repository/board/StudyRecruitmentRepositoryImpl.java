package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.olivejua.study.domain.board.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.board.QTechStack.techStack;

public class StudyRecruitmentRepositoryImpl implements StudyRecruitmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StudyRecruitmentRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<StudyRecruitment> search() {
        return queryFactory
                .selectFrom(studyRecruitment)
                .join(studyRecruitment.techStack, techStack)
                .fetch();
    }

    @Override
    public List<PostListResponseDto> list(SearchDto searchDto) {
//        BooleanExpression cond =
//                switch (searchDto.getSearchType()) {
//                    case TITLE -> titleEq(searchDto.getKeyword());
//                    case PLACE -> placeEq(searchDto.getKeyword());
//                    case EXPLANATION -> explanationEq(searchDto.getKeyword());
//                };

        return queryFactory
                .select(Projections.constructor(PostListResponseDto.class,
                        studyRecruitment.id,
                        studyRecruitment.title,
                        studyRecruitment.writer.name,
                        studyRecruitment.viewCount,
                        studyRecruitment.comment.size()))
                .from(studyRecruitment)
//                .where(cond)
                .fetch();
    }


    private BooleanExpression titleEq(String title) {
        return title != null ? studyRecruitment.title.eq(title) : null;
    }

    private BooleanExpression placeEq(String place) {
        return place != null ? studyRecruitment.condition.place.eq(place) : null;
    }

    private BooleanExpression explanationEq(String explanation) {
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
