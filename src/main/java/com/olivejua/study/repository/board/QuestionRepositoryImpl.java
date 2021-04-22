package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.olivejua.study.domain.board.QQuestion.question;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Question> findByTitle(String title) {
        return queryFactory
                .selectFrom(question)
                .where(question.title.eq(title))
                .fetch();
    }
}
