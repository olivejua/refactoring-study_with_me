package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.olivejua.study.domain.QComment.comment;
import static com.olivejua.study.domain.QUser.user;
import static com.olivejua.study.domain.board.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.board.QTechStack.techStack;

@RequiredArgsConstructor
@Repository
public class StudyRecruitmentQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * entity 전체목록 조회
     */
    public Page<PostListResponseDto> findEntities(Pageable pageable) {
        List<PostListResponseDto> content = selectEntities(pageable);

        JPAQuery<StudyRecruitment> countQuery =
                    queryFactory
                        .selectFrom(studyRecruitment);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 검색목록 조회
     */
    public Page<PostListResponseDto> findEntitiesWith(SearchDto cond, Pageable pageable) {
        List<PostListResponseDto> content = selectEntities(pageable, cond);
        JPAQuery<StudyRecruitment> countQuery = queryFactory
                .selectFrom(studyRecruitment)
                .where(
                        titleContains(cond),
                        placeContains(cond),
                        explanationContains(cond),
                        techStackContains(cond)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 1개 조회
     */
    public Optional<StudyRecruitment> findEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(studyRecruitment)
                .innerJoin(studyRecruitment.writer, user)
                .leftJoin(studyRecruitment.techStack, techStack)
                .leftJoin(studyRecruitment.comment, comment)
                .where(idEq(postId))
                .fetchOne());
    }

    /**
     * 목록 조회 쿼리
     */
    private List<PostListResponseDto> selectEntities(Pageable pageable) {
        return selectEntities(pageable, null);
    }

    private List<PostListResponseDto> selectEntities(Pageable pageable, SearchDto cond) {
        List<StudyRecruitment> entities = queryFactory
                .selectFrom(studyRecruitment)
                .innerJoin(studyRecruitment.writer, user)
                .leftJoin(studyRecruitment.techStack, techStack)
                .leftJoin(studyRecruitment.comment, comment)
                .where(
                        titleContains(cond),
                        placeContains(cond),
                        explanationContains(cond),
                        techStackContains(cond)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return toPostListResponseDtos(entities);
    }

    /**
     * Column 조건
     */
    private List<PostListResponseDto> toPostListResponseDtos(List<StudyRecruitment> entities) {
        return entities.stream()
                .map(entity -> new PostListResponseDto(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getWriter().getName(),
                        entity.getViewCount(),
                        entity.getComment().size()
                )).collect(Collectors.toList());
    }

    private BooleanExpression idEq(Long postId) {
        return studyRecruitment.id.eq(postId);
    }

    private BooleanExpression titleContains(SearchDto cond) {
        if (cond != null
                && cond.getSearchType() == SearchType.TITLE
                && !StringUtils.isEmpty(cond.getKeyword()))
            return studyRecruitment.title.contains(cond.getKeyword());

        return null;
    }

    private BooleanExpression placeContains(SearchDto cond) {
        if (cond != null
                && cond.getSearchType() == SearchType.PLACE
                && StringUtils.isEmpty(cond.getKeyword()))
            return studyRecruitment.condition.place.contains(cond.getKeyword());

        return null;
    }

    private BooleanExpression explanationContains(SearchDto cond) {
        if (cond != null
                && cond.getSearchType() == SearchType.EXPLANATION
                && StringUtils.isEmpty(cond.getKeyword()))
            return studyRecruitment.condition.explanation.contains(cond.getKeyword());

        return null;
    }

    private BooleanExpression techStackContains(SearchDto cond) {
        if (cond != null
                && cond.getSearchType() == SearchType.TECH_STACK
                && StringUtils.isEmpty(cond.getKeyword()))
            return techStack.element.contains(cond.getKeyword());

        return null;
    }
}
