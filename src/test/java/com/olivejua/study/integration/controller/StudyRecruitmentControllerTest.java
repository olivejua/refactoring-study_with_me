package com.olivejua.study.integration.controller;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static com.olivejua.study.utils.ApiUrlPaths.POSTS;
import static com.olivejua.study.utils.ApiUrlPaths.STUDY_RECRUITMENT;
import static org.apache.http.HttpHeaders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudyRecruitmentControllerTest extends CommonControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private User testUser;
    private String accessToken;

    @BeforeEach
    void setup() {
        testUser = userFactory.user();
        accessToken = makeAccessToken(testUser.getId());
    }

    @Test
    @DisplayName("스터디 모집 게시글을 작성한다")
    void testSave() throws Exception {
        User author = testUser;

        StudyRecruitmentSaveRequestDto requestDto = StudyRecruitmentSaveRequestDto.builder()
                .title("test title")
                .techs(List.of("java", "jpa", "spring"))
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(10)
                .explanation("test explanation")
                .build();

        mockMvc.perform(post(STUDY_RECRUITMENT + POSTS)
                .header(AUTHORIZATION, accessToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    private String makeAccessToken(Long userId) {
        return "Bearer " + jwtTokenProvider.createTokenForUser(userId);
    }
}
