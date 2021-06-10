package com.olivejua.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.service.CommentService;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession httpSession;

    @MockBean
    private CommentService commentService;

    private Question post;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        httpSession = new MockHttpSession();


        User postWriter = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google");
        em.persist(postWriter);

        post = Question.savePost(postWriter, "sample title", "sample content");
        em.persist(post);
    }

    @Test
    @DisplayName("댓글을 작성한다")
    public void saveTest() throws Exception {
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(post.getId(), "sample content1");

        User commentWriter = User.createUser("user2", "user2@example.com", Role.USER, "google");
        em.persist(commentWriter);
        httpSession.setAttribute("user", new SessionUser(commentWriter));

        mvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글을 수정한다")
    public void updateTest() throws Exception {
        when(commentService.update(anyLong(), anyString())).thenReturn(1L);

        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(post.getId(), "updated sample content2");

        mvc.perform(put("/comment/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    public void deleteTest() throws Exception {
        when(commentService.delete(anyLong())).thenReturn(1L);

        mvc.perform(delete("/comment/{commentId}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
