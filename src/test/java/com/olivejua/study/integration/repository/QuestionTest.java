package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.Question;
import com.olivejua.study.domain.User;
import com.olivejua.study.integration.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionTest extends IntegrationTest {

    @Test
    void save() {
        //Given
        User author = userFactory.user();

        //When
        Question post = questionFactory.post(author);

        //Then
        assertNotNull(post.getId());
        assertEquals(author.getName(), post.getNameOfAuthor());
    }
}
