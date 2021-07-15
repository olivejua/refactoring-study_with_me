package com.olivejua.study.unit.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.unit.repository.UserRepository;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionQueryRepository questionQueryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Question - 저장")
    void save() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        Question post = SampleQuestion.create(writer);
        questionRepository.save(post);

        Question findPost = questionRepository.findAll().get(0);

        assertEquals(post, findPost);
    }

    @Test
    void list() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        for (int i=0; i<100; i++) {
            questionRepository.save(SampleQuestion.create(writer));
        }

        em.flush();
        em.clear();


        PageRequest paging = PageRequest.of(0, 10, Sort.Direction.ASC, "POST_ID");

        Page<PostListResponseDto> posts = questionQueryRepository.findEntities(paging);
        assertEquals(10, posts.getNumberOfElements());
    }

    @Test
    void search() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        String[] titles = new String[5];
        for (int i=0; i< titles.length; i++) {
            titles[i] = "제목"+(i+1);
        }

        for (int i=0; i<20; i++) {
            questionRepository.save(Question.savePost(
                    writer,
                    titles[i%titles.length],
                    "샘플 내용"
            ));
        }

        SearchDto searchDto = new SearchDto("TITLE", "제목3");
        PageRequest paging = PageRequest.of(0, 10, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> savedPosts = questionQueryRepository.findEntitiesWith(searchDto, paging);

        assertEquals(4, savedPosts.getTotalElements());
    }
}