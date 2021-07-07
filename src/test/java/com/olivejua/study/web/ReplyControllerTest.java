package com.olivejua.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.service.ReplyService;
import com.olivejua.study.web.dto.reply.ReplySaveRequestDto;
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

public class ReplyControllerTest extends CommonControllerTest {

    @MockBean
    private ReplyService replyService;

    private Question dummyPost;

    private Comment dummyComment;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        super.setup(webApplicationContext);

        User postWriter = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google");
        User commentWriter = User.createUser(
                "user2",
                "user2@gmail.com",
                Role.USER,
                "google");
        em.persist(postWriter);
        em.persist(commentWriter);

        dummyPost = Question.savePost(postWriter, "sample title", "sample content");
        em.persist(dummyPost);
        dummyComment = Comment.createComment(dummyPost, commentWriter, "sample content");
        em.persist(dummyComment);
    }

    @Test
    @DisplayName("대댓글을 작성한다")
    public void saveTest() throws Exception {
        ReplySaveRequestDto requestDto =
                new ReplySaveRequestDto(dummyComment.getId(), "sample reply content1");

        User replyWriter
                = User.createUser("user3", "user3@gmail.com", Role.USER, "google");
        em.persist(replyWriter);
        httpSession.setAttribute("user", new SessionUser(replyWriter));

        mvc.perform(post("/reply")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("대댓글을 수정한다")
    public void updateTest() throws Exception {
        ReplySaveRequestDto requestDto =
                new ReplySaveRequestDto(dummyComment.getId(), "updated content2");

        doNothing().when(replyService).update(anyLong(), anyString());

        mvc.perform(put("/reply/{replyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("대댓글을 삭제한다")
    public void deleteTest() throws Exception {
        doNothing().when(replyService).delete(anyLong());

        mvc.perform(delete("/reply/{replyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
