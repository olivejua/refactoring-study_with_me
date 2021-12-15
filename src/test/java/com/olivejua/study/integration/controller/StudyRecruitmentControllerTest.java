package com.olivejua.study.integration.controller;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.utils.ErrorCodes;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

import static com.olivejua.study.utils.ApiUrlPaths.POSTS;
import static com.olivejua.study.utils.ApiUrlPaths.STUDY_RECRUITMENT;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudyRecruitmentControllerTest extends CommonControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private User testUser;
    private String accessToken;

    @BeforeEach
    void setup() {
        testUser = userFactory.user();
        accessToken = getAccessToken(testUser.getId());
    }

    @Test
    @DisplayName("스터디 모집 게시글을 작성한다")
    void testSave() throws Exception {
        StudyRecruitmentSaveRequestDto requestDto = StudyRecruitmentSaveRequestDto.builder()
                .title("test title")
                .techs(List.of("java", "jpa", "spring boot"))
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true))
                .andDo(document("study-recruitment/posts/save",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("techs").description("스터디에서 사용할 기술 스택"),
                                fieldWithPath("meetingPlace").description("만남 장소 (지역)"),
                                fieldWithPath("startDate").description("스터디 시작일자"),
                                fieldWithPath("endDate").description("스터디 종료일자"),
                                fieldWithPath("capacity").description("최대 인원 수"),
                                fieldWithPath("explanation").description("추가 설명")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 처리 여부")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("스터디 모집 게시글을 작성할 때 유효성 검사가 실패한다")
    void testSave_Error() throws Exception {
        StudyRecruitmentSaveRequestDto requestDto = StudyRecruitmentSaveRequestDto.builder()
                .title("")
                .techs(List.of("java", "jpa", "spring boot"))
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(-1)
                .explanation("test explanation")
                .build();

        mockMvc.perform(post(STUDY_RECRUITMENT + POSTS)
                        .header(AUTHORIZATION, accessToken)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("code").value(ErrorCodes.Global.CONSTRAINT_VIOLATION_EXCEPTION))
                .andExpect(jsonPath("message").exists())
        ;
    }

    @Test
    @DisplayName("스터디 모집 게시글을 수정한다")
    void testUpdate() throws Exception {
        StudyRecruitmentUpdateRequestDto requestDto = StudyRecruitmentUpdateRequestDto.builder()
                .title("updated title")
                .techs(List.of("node.js", "typescript"))
                .meetingPlace("홍대")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .capacity(5)
                .explanation("updated explanation")
                .build();

        mockMvc.perform(put(STUDY_RECRUITMENT + POSTS)
                .header(AUTHORIZATION, accessToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andDo(document("study-recruitment/posts/save",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("techs").description("스터디에서 사용할 기술 스택"),
                                fieldWithPath("meetingPlace").description("만남 장소 (지역)"),
                                fieldWithPath("startDate").description("스터디 시작일자"),
                                fieldWithPath("endDate").description("스터디 종료일자"),
                                fieldWithPath("capacity").description("최대 인원 수"),
                                fieldWithPath("explanation").description("추가 설명")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").description("성공 처리 여부")
                        )
                ));
    }
}
