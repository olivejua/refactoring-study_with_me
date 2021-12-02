package com.olivejua.study.integration.web;

import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.service.StudyService;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StudyControllerTest extends CommonControllerTest {

    @MockBean
    private StudyService studyService;

    @Test
    @DisplayName("[StudyRecruitment] 게시글을 하나 조회한다")
    public void readTest() throws Exception {
        User writer = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google");
        em.persist(writer);

        List<String> techStack = new ArrayList<>();
        techStack.add("Spring");
        techStack.add("Java");
        techStack.add("Jpa");

        Condition condition = Condition.createCondition(
                "sample place1",
                LocalDate.of(2021, 06, 10),
                LocalDate.of(2021, 12, 10),
                10,
                "sample explanation"
        );

        StudyRecruitment post =
                StudyRecruitment.createPost(writer, "sample title1", techStack, condition);

        em.persist(post);
        post.getTechStack().forEach(em::persist);

        when(studyService.read(anyLong(), anyString())).thenReturn(new PostReadResponseDto(post));

        mvc.perform(get("/study/"+post.getId())
                .param("page", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.writer").isNotEmpty())
                .andExpect(jsonPath("$.techStack").isArray())
                .andDo(print());
    }

    @Test
    @DisplayName("[StudyRecruitment] 게시글을 작성한다")
    public void saveTest() throws Exception {
        User user = User.createUser(
                "current user", "user1@gmail.com", Role.USER, "google");
        em.persist(user);
        httpSession.setAttribute("user", new SessionUser(user));

        List<String> techStack = new ArrayList<>();
        techStack.add("Spring");
        techStack.add("Java");
        techStack.add("Jpa");

        PostSaveRequestDto requestDto = new PostSaveRequestDto(
                "sample title",
                techStack,
                "sample place",
                LocalDate.of(2021, 06, 10),
                LocalDate.of(2021, 12, 10),
                10,
                "sample explanation");

        mvc.perform(post("/study")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("[StudyRecruitment] 게시글을 수정한다")
    public void updateTest() throws Exception {
        List<String> techStack = new ArrayList<>();
        techStack.add("Spring");
        techStack.add("Java");
        techStack.add("Jpa");

        PostSaveRequestDto requestDto = new PostSaveRequestDto(
                "sample title",
                techStack,
                "sample place",
                LocalDate.of(2021, 06, 10),
                LocalDate.of(2021, 12, 10),
                10,
                "sample explanation");

        when(studyService.update(anyLong(), any())).thenReturn(1L);

        mvc.perform(put("/study/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("[StudyRecruitment] 게시글을 삭제한다")
    public void deleteTest() throws Exception {
        when(studyService.delete(anyLong())).thenReturn(anyLong());

        mvc.perform(delete("/study/{postId}", 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
