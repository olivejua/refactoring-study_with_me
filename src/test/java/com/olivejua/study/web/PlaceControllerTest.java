package com.olivejua.study.web;

import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.service.PlaceService;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlaceControllerTest extends CommonControllerTest {

    @MockBean
    private PlaceService placeService;

    @Test
    @DisplayName("[PlaceRecommendation] 게시글을 하나 조회한다")
    public void readTest() throws Exception {
        User writer = User.createUser(
                "user1",
                "user1@gmail.com",
                Role.USER,
                "google"
        );
        em.persist(writer);
        httpSession.setAttribute("user", new SessionUser(writer));

        List<String> links = new ArrayList<>();
        links.add("www.example1.com");
        links.add("www.example2.com");
        links.add("www.example3.com");

        PlaceRecommendation post = PlaceRecommendation.savePost(
                writer,
                "sample title1",
                "sample address1",
                "sample addressDetail1",
                "thumbnail path1",
                "sample content1",
                links);

        em.persist(post);
        post.getLinks().forEach(em::persist);

        PostReadResponseDto responseDto = new PostReadResponseDto(post, null);
        when(placeService.read(anyLong(), any(), anyString())).thenReturn(responseDto);

        mvc.perform(get("/place/{postId}", 1L)
                .param("page", String.valueOf(0))
                .session(httpSession)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.writer").isNotEmpty())
                .andExpect(jsonPath("$.links").isArray())
                .andExpect(jsonPath("$.address").isNotEmpty())
                .andExpect(jsonPath("$.likeCount").isNotEmpty())
                .andExpect(jsonPath("$.likeStatus").isNotEmpty())
                .andExpect(jsonPath("$.comments").isArray())
                .andDo(print());
    }

    @Test
    @DisplayName("[PlaceRecommendation] 게시글을 작성한다")
    public void saveTest() throws Exception {
        User user =
                User.createUser("current user", "user1@gmail.com", Role.USER, "google");
        em.persist(user);
        httpSession.setAttribute("user", new SessionUser(user));

        List<String> links = new ArrayList<>();
        links.add("www.example1.com");
        links.add("www.example2.com");
        links.add("www.example3.com");

        PostSaveRequestDto requestDto = new PostSaveRequestDto("sample title1",
                "sample address1",
                "sample addressDetail1",
                links,
                "sample thumbnailPath",
                "sample content");

        when(placeService.post(any(), any())).thenReturn(1L);

        mvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("[PlaceRecommendation] 게시글을 수정한다")
    public void updateTest() throws Exception {
        List<String> links = new ArrayList<>();
        links.add("www.example1.com");
        links.add("www.example2.com");
        links.add("www.example3.com");

        PostSaveRequestDto requestDto = new PostSaveRequestDto("sample title1",
                "sample address1",
                "sample addressDetail1",
                links,
                "sample thumbnailPath",
                "sample content");

        when(placeService.update(anyLong(), any())).thenReturn(1L);

        mvc.perform(put("/place/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("[PlaceRecommendation] 게시글을 삭제한다")
    public void deleteTest() throws Exception {
        doNothing().when(placeService).delete(anyLong());

        mvc.perform(delete("/place/{postId}", 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
