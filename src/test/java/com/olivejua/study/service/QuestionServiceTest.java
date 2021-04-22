package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserRepository userRepository;

    private User writer;

    @BeforeEach
    void setup() {
        writer = userRepository.save(SampleUser.create());
    }

    @Test
    void post() {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("JPA 관련 질문입니다.")
                .content("JPA에서 쿼리가 나가는 시점은?")
                .build();

        Long savedPostId = questionService.post(requestDto, writer);

        PostReadResponseDto responseDto = questionService.findPostBy(savedPostId);

        assertEquals(writer.getId(), responseDto.getWriter().getId());
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
    }

    @Test
    void findPostBy_x() {
        assertThrows(IllegalArgumentException.class,
                () -> questionService.findPostBy(1L),
                "저장하지 않은 게시물을 가져오는 호출에는 예외가 발생해야한다."
        );
    }
}