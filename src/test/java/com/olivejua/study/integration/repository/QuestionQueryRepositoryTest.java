package com.olivejua.study.integration.repository;

import com.olivejua.study.repository.board.QuestionQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class QuestionQueryRepositoryTest extends CommonRepositoryTest {

    @Override
    void setup() {

    }

    @Override
    void clearAll() {

    }

    @Autowired
    QuestionQueryRepository questionQueryRepository;

    @Test
    void testSave() {
        questionQueryRepository.findEntities(PageRequest.of(0, 10));
    }
}
