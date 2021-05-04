package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.olivejua.study.domain.board.QQuestion.question;

@RequiredArgsConstructor
@Repository
public class QuestionQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(question)
                .where(question.id.eq(id))
                .fetchOne());
    }
}
