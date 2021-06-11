package com.olivejua.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.service.QuestionService;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService questionService;

    private MockHttpSession httpSession;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        httpSession = new MockHttpSession();
    }

    @Test
    @DisplayName("[Question] 게시글을 하나 조회한다")
    public void readTest() throws Exception {
        User writer = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google");
        em.persist(writer);

        Question post = Question.savePost(
                writer, "sample title1", "sample content1");
        em.persist(post);

        when(questionService.read(anyLong())).thenReturn(new PostReadResponseDto(post));

        mvc.perform(get("/question/"+post.getId())
                .param("page", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.viewCount").isNotEmpty())
                .andExpect(jsonPath("$.comments").isArray())
                .andDo(print());
    }

    @Test
    @DisplayName("[Question] 게시글을 작성한다")
    public void postTest() throws Exception {
        PostSaveRequestDto requestDto =
                new PostSaveRequestDto("sample title1 in a new post", "sample content1 in a new post");

        User user = User.createUser(
                "current user", "user1@gmail.com", Role.USER, "google");
        em.persist(user);
        httpSession.setAttribute("user", new SessionUser(user));

        mvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("[Question] 게시글을 수정한다")
    public void updateTest() throws Exception {
        //given
        User user = User.createUser(
                "current user", "user1@gmail.com", Role.USER, "google");
        em.persist(user);
        httpSession.setAttribute("user", new SessionUser(user));

        Question post = Question.savePost(user, "sample title", "sample content");
        em.persist(post);

        //when
        PostUpdateRequestDto requestDto =
                new PostUpdateRequestDto("updated title", "updated content");

        //then
        mvc.perform(put("/question/"+post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("[Question] 게시글을 삭제한다")
    public void deleteTest() throws Exception {
        //given
        User user = User.createUser(
                "current user", "user1@gmail.com", Role.USER, "google");
        em.persist(user);
        httpSession.setAttribute("user", new SessionUser(user));

        //when
        when(questionService.delete(anyLong())).thenReturn(anyLong());

        //then
        mvc.perform(delete("/question/"+1L))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(questionService, atLeastOnce()).delete(anyLong());
    }
}
