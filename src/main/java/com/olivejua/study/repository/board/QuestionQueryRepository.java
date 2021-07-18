package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Question;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
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
import static com.olivejua.study.domain.board.QQuestion.question;

@RequiredArgsConstructor
@Repository
public class QuestionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * entity 전체목록 조회
     */
    public Page<PostListResponseDto> findEntities(Pageable pageable) {
        List<PostListResponseDto> content = selectEntities(pageable);
        JPAQuery<Question> countQuery = jpaQueryFactory
                .selectFrom(question);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 검색목록 조회
     */
    public Page<PostListResponseDto> findEntitiesWith(SearchDto cond, Pageable pageable) {
        List<PostListResponseDto> content = selectEntities(pageable, cond);
        JPAQuery<Question> countQuery = jpaQueryFactory
                .selectFrom(question)
                .where(allEq(cond));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 1개 조회
     */
    public Optional<Question> findEntity(Long postId) {
        return Optional.ofNullable(
                jpaQueryFactory
                .selectFrom(question)
                .innerJoin(question.writer, user)
                .leftJoin(question.comment, comment)
                .fetchJoin()
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
        List<Question> entities = jpaQueryFactory
                .selectFrom(question)
                .innerJoin(question.writer, user)
                .leftJoin(question.comment, comment)
                .fetchJoin()
                .where(allEq(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return toPostListResponseDtos(entities);
    }

    /**
     * List<Entity> 에서 List<postId>만 추출
     */
    private List<PostListResponseDto> toPostListResponseDtos(List<Question> entities) {
        return entities.stream()
                .map(entity -> new PostListResponseDto(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getWriter().getName(),
                        entity.getViewCount(),
                        entity.getComment().size(),
                        entity.getCreatedDate()
                )).collect(Collectors.toList());
    }

    /**
     * Column 조건
     */
    private BooleanExpression allEq(SearchDto cond) {
        if (cond==null) {
            return null;
        }
        return titleContains(cond)
                .and(writerEq(cond))
                .and(contentContains(cond));
    }

    private BooleanExpression idEq(Long postId) {
        return question.id.eq(postId);
    }

    private BooleanExpression titleContains(SearchDto cond) {
        return cond.getSearchType() == SearchType.TITLE && cond.getKeyword() != null
                ? question.title.contains(cond.getKeyword()) : null;
    }

    private BooleanExpression writerEq(SearchDto cond) {
        return cond.getSearchType() == SearchType.WRITER && cond.getKeyword() != null
                ? question.writer.name.contains(cond.getKeyword()) : null;
    }

    private BooleanExpression contentContains(SearchDto cond) {
        return cond.getSearchType() == SearchType.CONTENT && cond.getKeyword() != null
                ? question.content.contains(cond.getKeyword()) : null;
    }
}
