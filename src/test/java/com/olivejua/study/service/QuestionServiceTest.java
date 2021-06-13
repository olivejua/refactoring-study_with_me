package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest extends CommonBoardServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @AfterEach
    void cleanup() {
        replyRepository.deleteAll();
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void post() {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("test post sample title")
                .content("test post sample content")
                .build();

        Long postId = questionService.post(requestDto, dummyPostWriter);

        Question savedPost = questionRepository.findById(postId).orElse(null);

        assertNotNull(savedPost);
        assertEquals(requestDto.getTitle(), savedPost.getTitle());
        assertEquals(requestDto.getContent(), savedPost.getContent());
    }

    @Test
    void findPostBy_x() {
        assertThrows(IllegalArgumentException.class,
                () -> questionService.read(1000L, dummyServletPath),
                "저장하지 않은 게시물을 가져오는 호출에는 예외가 발생해야한다."
        );
    }

    @Test
    void update() {
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title("test update sample title")
                .content("test update sample content")
                .build();

        questionService.update(dummyPost.getId(), requestDto);
        Question updatedPost = questionRepository.findById(dummyPost.getId()).orElse(null);

        assertNotNull(updatedPost);
        assertEquals(requestDto.getTitle(), updatedPost.getTitle());
        assertEquals(requestDto.getContent(), updatedPost.getContent());
    }

    @Test
    void delete() {
        questionService.delete(dummyPost.getId());
        Optional<Question> findEntity = questionRepository.findById(dummyPost.getId());

        assertFalse(findEntity.isPresent());
    }

    @Test
    void list() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        for (int i=0; i<100; i++) {
            questionRepository.save(SampleQuestion.create(writer));
        }

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> list = questionService.list(paging);

        assertEquals(20, list.getSize());
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
        Page<PostListResponseDto> list = questionService.search(searchDto, paging);

        assertEquals(4, list.getTotalElements());
    }

    @Override
    void saveDummyPost(Board post) {
        if (post instanceof Question) {
            Question postInQuestion = (Question) post;
            questionRepository.save(postInQuestion);

            dummyPost = postInQuestion;
        }
    }

    @Override
    Board createDummyPost() {
        return Question.savePost(
                dummyPostWriter, "sample title1", "sample content1");
    }
}