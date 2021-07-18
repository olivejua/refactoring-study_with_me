package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceQueryRepositoryTest extends CommonRepositoryTest {

    @Autowired
    private QuestionQueryRepository questionQueryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    void setup() {
        samplePosts = createSamplePosts(20);
    }

    @Override
    void clearAll() {
        questionRepository.deleteAll();
    }

    @Test
    void testFindEntity() {
        //given

        //when
        Question findPost = questionQueryRepository.findEntity(0L).orElse(null);

        //then
        assertNotNull(findPost);

    }

    private List<Question> createSamplePosts(int size) {
        List<Question> posts = new ArrayList<>();

        for (int i=1; i<=size; i++) {
            Question post = Question.savePost(sampleWriter, "sample title" + i, "sample content" + i);
            questionRepository.save(post);

            posts.add(post);
        }

        return posts;
    }
}
