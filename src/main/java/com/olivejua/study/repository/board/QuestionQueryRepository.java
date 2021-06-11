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

    private final JPAQueryFactory queryFactory;

    public Optional<Question> findEntity(Long postId) {
        return Optional.ofNullable(
                queryFactory
                .selectFrom(question)
                .innerJoin(question.writer, user)
                .leftJoin(question.comment, comment)
                .fetchJoin()
                .where(idEq(postId))
                .fetchOne());
    }

    public Page<PostListResponseDto> list(Pageable pageable) {
        List<PostListResponseDto> content = selectPosts(pageable);
        JPAQuery<Question> countQuery = queryFactory
                .selectFrom(question);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public Page<PostListResponseDto> search(SearchDto cond, Pageable pageable) {
        List<PostListResponseDto> content = selectPosts(pageable, cond);
        JPAQuery<Question> countQuery = queryFactory
                .selectFrom(question)
                .where(allEq(cond));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private List<PostListResponseDto> selectPosts(Pageable pageable) {
        return selectPosts(pageable, null);
    }

    private List<PostListResponseDto> selectPosts(Pageable pageable, SearchDto cond) {
        List<Question> entities = queryFactory
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
