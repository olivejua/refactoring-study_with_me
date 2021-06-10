package com.olivejua.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.service.PlaceService;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession httpSession;

    @MockBean
    private PlaceService placeService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        httpSession = new MockHttpSession();
    }

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

        when(placeService.read(anyLong(), any())).thenReturn(new PostReadResponseDto(post, null));

        mvc.perform(get("/place/{postId}", 1L)
                .param("page", String.valueOf(0))
                .session(httpSession))
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

        mvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[PlaceRecommendation] 게시글을 삭제한다")
    public void deleteTest() throws Exception {
        doNothing().when(placeService).delete(anyLong());

        mvc.perform(delete("/place/{postId}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
