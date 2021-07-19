package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionRepositoryTest extends CommonBoardRepositoryTest {

    /**
     * Dependency Injection
     */
    @Autowired
    private QuestionQueryRepository questionQueryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        questionRepository.deleteAll();
    }


    /**
     * test zone
     */



    /**
     * overriding zone
     */
    @Override
    Board createDummyPost() {
        Question post = Question.savePost(dummyWriter, "sample title", "sample content");
        return questionRepository.save(post);
    }
}
