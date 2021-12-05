package com.olivejua.study.integration.mockFactory;

import com.olivejua.study.common.mockData.MockQuestion;
import com.olivejua.study.domain.Question;
import com.olivejua.study.domain.User;
import com.olivejua.study.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class QuestionFactory {
    @Autowired
    private QuestionRepository questionRepository;

    private static final String MOCK_TITLE = "I have a question";
    private static final String MOCK_CONTENT = "Why do you want to be a developer?";

    /****************************************************************************
     * Create a Post
     ***************************************************************************/
    public Question post(User author) {
        Question post = MockQuestion.builder()
                .author(author)
                .title(MOCK_TITLE)
                .content(MOCK_CONTENT)
                .build();

        return questionRepository.save(post);
    }
}
