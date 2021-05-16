package com.olivejua.study.repository.board;

import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.olivejua.study.domain.board.QQuestion.question;

@RequiredArgsConstructor
@Repository
public class QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<PostListResponseDto> list(Pageable pageable) {
        QueryResults<PostListResponseDto> results = queryFactory
                .selectDistinct(Projections.constructor(PostListResponseDto.class,
                        question.id,
                        question.title,
                        question.writer.name,
                        question.viewCount,
                        question.comment.size(),
                        question.createdDate))
                .from(question)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    public Page<PostListResponseDto> search(SearchDto cond, Pageable pageable) {
        QueryResults<PostListResponseDto> results = queryFactory
                .select(Projections.constructor(PostListResponseDto.class,
                        question.id,
                        question.title,
                        question.writer.name,
                        question.viewCount,
                        question.comment.size(),
                        question.createdDate))
                .from(question)
                .where(titleContains(cond),
                        writerEq(cond),
                        contentContains(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleContains(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.TITLE && searchDto.getKeyword() != null
                ? question.title.contains(searchDto.getKeyword()) : null;
    }

    private BooleanExpression writerEq(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.WRITER && searchDto.getKeyword() != null
                ? question.writer.name.contains(searchDto.getKeyword()) : null;
    }

    private BooleanExpression contentContains(SearchDto searchDto) {
        return searchDto.getSearchType() == SearchType.CONTENT && searchDto.getKeyword() != null
                ? question.content.contains(searchDto.getKeyword()) : null;
    }
}
