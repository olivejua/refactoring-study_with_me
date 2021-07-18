package com.olivejua.study.unit.repository;

import com.olivejua.study.unit.repository.board.QuestionQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class QuestionQueryRepositoryTest extends CommonRepositoryTest {

    @Autowired
    QuestionQueryRepository questionQueryRepository;

    @Test
    void testSave() {
        questionQueryRepository.findEntities(PageRequest.of(0, 10));
    }
}
