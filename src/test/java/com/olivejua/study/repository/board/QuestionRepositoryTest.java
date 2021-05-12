package com.olivejua.study.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Question - 저장")
    public void save() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        Question post = SampleQuestion.create(writer);
        questionRepository.save(post);

        Question findPost = questionRepository.findAll().get(0);

        Assertions.assertEquals(post, findPost);
    }
}