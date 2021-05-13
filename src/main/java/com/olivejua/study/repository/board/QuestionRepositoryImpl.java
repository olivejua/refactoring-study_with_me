package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.QQuestion;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.olivejua.study.domain.board.QQuestion.*;

public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
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

    @Override
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
