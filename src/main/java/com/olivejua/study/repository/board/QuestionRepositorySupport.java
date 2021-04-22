package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.QQuestion;
import com.olivejua.study.domain.board.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.olivejua.study.domain.board.QQuestion.*;

@Repository
public class QuestionRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public QuestionRepositorySupport(JPAQueryFactory queryFactory) {
        super(Question.class);
        this.queryFactory = queryFactory;
    }

    public List<Question> findByTitle(String title) {
        return queryFactory
                .selectFrom(question)
                .where(question.title.eq(title))
                .fetch();
    }
}
