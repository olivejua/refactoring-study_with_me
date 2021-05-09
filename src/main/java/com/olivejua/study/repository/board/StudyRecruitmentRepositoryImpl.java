package com.olivejua.study.repository.board;

import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.olivejua.study.web.dto.board.study.SearchType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public Page<PostListResponseDto> list(Pageable pageable) {
        QueryResults<PostListResponseDto> results = queryFactory
                .selectDistinct(Projections.constructor(PostListResponseDto.class,
                        studyRecruitment.id,
                        studyRecruitment.title,
                        studyRecruitment.writer.name,
                        studyRecruitment.viewCount,
                        studyRecruitment.comment.size()))
                .from(studyRecruitment)
                .join(studyRecruitment.techStack, techStack)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PostListResponseDto> search(SearchDto cond, Pageable pageable) {
        QueryResults<PostListResponseDto> results = queryFactory
                .select(Projections.constructor(PostListResponseDto.class,
                        studyRecruitment.id,
                        studyRecruitment.title,
                        studyRecruitment.writer.name,
                        studyRecruitment.viewCount,
                        studyRecruitment.comment.size()))
                .from(studyRecruitment)
                .join(studyRecruitment.techStack, techStack)
                .where(titleEq(cond),
                        placeEq(cond),
                        explanationEq(cond),
                        techStackContains(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression titleEq(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.TITLE && searchDto.getKeyword() != null
                ? studyRecruitment.title.eq(searchDto.getKeyword()) : null;
    }

    private BooleanExpression placeEq(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.PLACE && searchDto.getKeyword() != null
                ? studyRecruitment.condition.place.eq(searchDto.getKeyword()) : null;
    }

    private BooleanExpression explanationEq(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.EXPLANATION && searchDto.getKeyword() != null
                ? studyRecruitment.condition.explanation.eq(searchDto.getKeyword()) : null;
    }

    private BooleanExpression techStackContains(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.TECH_STACK && searchDto.getKeyword() != null
                ? techStack.element.contains(searchDto.getKeyword()) : null;
    }
}
