package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.QStudyRecruitment;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

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

    public Page<PostListResponseDto> list(Pageable pageable) {
        List<PostListResponseDto> content = findPosts(pageable);

        JPAQuery<StudyRecruitment> countQuery =
                    queryFactory
                        .selectFrom(studyRecruitment);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public Page<PostListResponseDto> search(SearchDto cond, Pageable pageable) {
        List<PostListResponseDto> content = findPosts(pageable, cond);
        JPAQuery<StudyRecruitment> countQuery = queryFactory
                .selectFrom(studyRecruitment)
                .where(allEq(cond));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public Optional<StudyRecruitment> findEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(QStudyRecruitment.studyRecruitment)
                .innerJoin(QStudyRecruitment.studyRecruitment.writer, user)
                .leftJoin(QStudyRecruitment.studyRecruitment.techStack, techStack)
                .leftJoin(QStudyRecruitment.studyRecruitment.comment, comment)
                .fetchJoin()
                .where(QStudyRecruitment.studyRecruitment.id.eq(postId))
                .fetchOne());
    }

    private List<PostListResponseDto> findPosts(Pageable pageable) {
        return findPosts(pageable, null);
    }

    private List<PostListResponseDto> findPosts(Pageable pageable, SearchDto cond) {
        List<StudyRecruitment> entities = queryFactory
                .selectFrom(studyRecruitment)
                .innerJoin(studyRecruitment.writer, user)
                .leftJoin(studyRecruitment.techStack, techStack)
                .leftJoin(studyRecruitment.comment, comment)
                .fetchJoin()
                .where(cond == null ? null : allEq(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return toPostListResponseDtos(entities);
    }

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

    private BooleanExpression allEq(SearchDto cond) {
        return titleEq(cond)
                .and(placeEq(cond))
                .and(explanationEq(cond))
                .and(techStackContains(cond));
    }

    private BooleanExpression titleEq(SearchDto cond) {
        return cond.getSearchType() == SearchType.TITLE && cond.getKeyword() != null
                ? studyRecruitment.title.contains(cond.getKeyword()) : null;
    }

    private BooleanExpression placeEq(SearchDto cond) {
        return cond.getSearchType() == SearchType.PLACE && cond.getKeyword() != null
                ? studyRecruitment.condition.place.contains(cond.getKeyword()) : null;
    }

    private BooleanExpression explanationEq(SearchDto cond) {
        return cond.getSearchType() == SearchType.EXPLANATION && cond.getKeyword() != null
                ? studyRecruitment.condition.explanation.contains(cond.getKeyword()) : null;
    }

    private BooleanExpression techStackContains(SearchDto cond) {
        return cond.getSearchType() == SearchType.TECH_STACK && cond.getKeyword() != null
                ? techStack.element.contains(cond.getKeyword()) : null;
    }
}
