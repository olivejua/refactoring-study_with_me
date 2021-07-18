package com.olivejua.study.unit.service;

import com.olivejua.study.domain.board.Question;
import com.olivejua.study.service.QuestionService;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceTest extends CommonServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionQueryRepository questionQueryRepository;

    private Question dummyPost;

    void setup() {
        dummyPost = createPost();
    }

    @Test
    void testPost() {
        //given
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("sample title")
                .content("sample content")
                .build();

        Long fakePostId = 1L;
        ReflectionTestUtils.setField(dummyPost, "id", fakePostId);

        //mocking
        when(questionRepository.save(any(Question.class))).thenReturn(dummyPost);
        doNothing().when(boardImageUploader).uploadImagesInQuestion(any(Question.class));

        //when
        Long savedPostId = questionService.post(requestDto, dummyUser);

        //then
        assertEquals(fakePostId, savedPostId);
    }

    @Test
    void testUpdate() {
        //given
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title("sample title updated")
                .content("sample content updated")
                .build();

        Long fakePostId = 1L;
        ReflectionTestUtils.setField(dummyPost, "id", fakePostId);

        //mocking
        when(questionRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(dummyPost));

        doNothing().when(boardImageUploader).updateImagesInQuestion(dummyPost);

        //when
        questionService.update(fakePostId, requestDto);

        //then
        assertEquals(fakePostId, dummyPost.getId());
        assertEquals(requestDto.getTitle(), dummyPost.getTitle());
        assertEquals(requestDto.getContent(), dummyPost.getContent());
    }

    private Question createPost() {
        return Question.savePost(dummyUser, "sample title", "sample content");
    }

}
