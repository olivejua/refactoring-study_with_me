package com.olivejua.study.web;

import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.service.CommentService;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends CommonControllerTest {

    @MockBean
    private CommentService commentService;

    private Question dummyPost;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        super.setup(webApplicationContext);

        User postWriter = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google");
        em.persist(postWriter);

        dummyPost = Question.savePost(postWriter, "sample title", "sample content");
        em.persist(dummyPost);
    }

    @Test
    @DisplayName("댓글을 작성한다")
    public void saveTest() throws Exception {
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(dummyPost.getId(), "sample content1");

        User commentWriter = User.createUser("user2", "user2@example.com", Role.USER, "google");
        em.persist(commentWriter);
        httpSession.setAttribute("user", new SessionUser(commentWriter));

        mvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글을 수정한다")
    public void updateTest() throws Exception {
        doNothing().when(commentService).update(anyLong(), anyString());

        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(dummyPost.getId(), "updated sample content2");

        mvc.perform(put("/comment/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    public void deleteTest() throws Exception {
        doNothing().when(commentService).delete(anyLong());

        mvc.perform(delete("/comment/{commentId}", 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
