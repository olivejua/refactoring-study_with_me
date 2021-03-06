package com.olivejua.study.integration.controller;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.service.upload.UploadService;
import com.olivejua.study.utils.ErrorCodes;
import com.olivejua.study.utils.PostImagePaths;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;

import static com.olivejua.study.utils.ApiUrlPaths.*;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudyRecruitmentControllerTest extends CommonControllerTest {
    private static final String DIRECT = "study-recruitment/";

    @BeforeEach
    void setup() {
        setupUserAndAccessToken();
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ??????????????? ?????? ????????????")
    void testSaveContainingImages() throws Exception {
        StudyRecruitmentSaveRequestDto requestDto = studyRecruitmentFactory.saveRequestDto();

        String baseImageUrl = "https://aws.s3.simplelog.com/" + PostImagePaths.STUDY_RECRUITMENT + "1/";
        when(uploadService.upload(anyList(), anyString())).thenReturn(List.of(baseImageUrl+"1.jpg", baseImageUrl+"2.jpg"));

        mockMvc.perform(multipart(STUDY_RECRUITMENT + POSTS)
                        .file("images", getMockImage().getBytes())
                        .file("images", getMockImage().getBytes())
                        .params(toParamsMap(requestDto))
                        .header(AUTHORIZATION, accessToken)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true))
        ;
    }

    private MultiValueMap<String, String> toParamsMap(StudyRecruitmentSaveRequestDto requestDto) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", requestDto.getTitle());
        requestDto.getTechs()
                .forEach(tech -> params.add("techs", tech));
        params.add("meetingPlace", requestDto.getMeetingPlace());
        params.add("startDate", requestDto.getStartDate().toString());
        params.add("endDate", requestDto.getEndDate().toString());
        params.add("capacity", String.valueOf(requestDto.getCapacity()));
        params.add("explanation", requestDto.getExplanation());

        return params;
    }

    @Test
    void testLocalDateToString() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ????????????")
    void testSave() throws Exception {
        StudyRecruitmentSaveRequestDto requestDto = studyRecruitmentFactory.saveRequestDto();

        mockMvc.perform(post(STUDY_RECRUITMENT + POSTS)
                .header(AUTHORIZATION, accessToken)
                .accept(APPLICATION_JSON)
                .params(toParamsMap(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true))
                .andDo(document(DIRECT + "create-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        requestParameters(
                                parameterWithName("title").description("????????? ??????"),
                                parameterWithName("techs").description("??????????????? ????????? ?????? ??????"),
                                parameterWithName("meetingPlace").description("?????? ?????? (??????)"),
                                parameterWithName("startDate").description("????????? ????????????"),
                                parameterWithName("endDate").description("????????? ????????????"),
                                parameterWithName("capacity").description("?????? ?????? ???"),
                                parameterWithName("explanation").description("?????? ??????")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("?????? ?????? ??????")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ????????? ??? ????????? ????????? ????????????")
    void testSave_Error() throws Exception {
        StudyRecruitmentSaveRequestDto requestDto = studyRecruitmentFactory.saveRequestDto("", -1);

        mockMvc.perform(post(STUDY_RECRUITMENT + POSTS)
                        .header(AUTHORIZATION, accessToken)
                        .accept(APPLICATION_JSON)
                        .params(toParamsMap(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("code").value(ErrorCodes.Global.CONSTRAINT_VIOLATION_EXCEPTION))
                .andExpect(jsonPath("message").exists())
        ;
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ????????????")
    void testUpdate() throws Exception {
        User author = testUser;
        StudyRecruitment post = studyRecruitmentFactory.post(author, List.of("java", "spring boot"));

        StudyRecruitmentUpdateRequestDto requestDto = studyRecruitmentFactory.updateRequestDto();

        mockMvc.perform(put(STUDY_RECRUITMENT+POSTS+VAR_POST_ID, post.getId())
                .header(AUTHORIZATION, accessToken)
                .accept(APPLICATION_JSON)
                .params(toParamsMap(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andDo(document(DIRECT + "update-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        requestParameters(
                                parameterWithName("title").description("????????? ??????"),
                                parameterWithName("techs").description("??????????????? ????????? ?????? ??????"),
                                parameterWithName("meetingPlace").description("?????? ?????? (??????)"),
                                parameterWithName("startDate").description("????????? ????????????"),
                                parameterWithName("endDate").description("????????? ????????????"),
                                parameterWithName("capacity").description("?????? ?????? ???"),
                                parameterWithName("explanation").description("?????? ??????")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("?????? ?????? ??????")
                        )
                ));
    }

    private MultiValueMap<String, String> toParamsMap(StudyRecruitmentUpdateRequestDto requestDto) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", requestDto.getTitle());
        requestDto.getTechs()
                .forEach(tech -> params.add("techs", tech));
        params.add("meetingPlace", requestDto.getMeetingPlace());
        params.add("startDate", requestDto.getStartDate().toString());
        params.add("endDate", requestDto.getEndDate().toString());
        params.add("capacity", String.valueOf(requestDto.getCapacity()));
        params.add("explanation", requestDto.getExplanation());

        return params;
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ????????????")
    void testDelete() throws Exception {
        User author = testUser;
        StudyRecruitment post = studyRecruitmentFactory.post(author);

        mockMvc.perform(delete(STUDY_RECRUITMENT+POSTS+VAR_POST_ID, post.getId())
                        .header(AUTHORIZATION, accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andDo(document(DIRECT + "delete-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("?????? ?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ????????? ?????? ????????? ????????????")
    void testGetOnePost() throws Exception {
        User author = testUser;
        StudyRecruitment post = studyRecruitmentFactory.post(author);

        mockMvc.perform(get(STUDY_RECRUITMENT+POSTS+VAR_POST_ID, post.getId())
                        .header(AUTHORIZATION, accessToken)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("content").exists())
                .andDo(document(DIRECT + "get-a-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("?????? ?????? ??????"),
                                subsectionWithPath("content").description("????????? ????????? ??????")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("id").type(NUMBER).description("????????? ID"),
                                fieldWithPath("author.id").type(NUMBER).description("????????? ID"),
                                fieldWithPath("author.name").type(STRING).description("????????? ??????"),
                                fieldWithPath("title").type(STRING).description("????????? ??????"),
                                fieldWithPath("techs").type(ARRAY).description("?????? ?????? ??????"),
                                fieldWithPath("meetingPlace").type(STRING).description("?????? ?????? (??????)"),
                                fieldWithPath("startDate").type(STRING).description("????????? ????????????"),
                                fieldWithPath("endDate").type(STRING).description("????????? ????????????"),
                                fieldWithPath("capacity").type(NUMBER).description("?????? ?????? ???"),
                                fieldWithPath("explanation").type(STRING).description("?????? ??????"),
                                fieldWithPath("createdDate").type(STRING).description("????????????")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("????????? ?????? ????????? ????????? ????????????")
    void testGetPosts() throws Exception {
        User authorA = userFactory.user("authorA");
        User authorB = userFactory.user("authorB");
        User authorC = userFactory.user("authorC");
        List<User> authors = List.of(authorA, authorB, authorC);

        for (User author : authors) {
            for (int i = 0; i < 5; i++) {
                studyRecruitmentFactory.post(author, String.format("test title %d written by %s", i, author.getName()));
            }
        }

        mockMvc.perform(get(STUDY_RECRUITMENT+POSTS)
                        .header(AUTHORIZATION, accessToken)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("pageInfo").exists())
                .andExpect(jsonPath("pageInfo.totalElements").value(15))
                .andDo(document(DIRECT + "get-posts",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("?????? ?????? ??????"),
                                subsectionWithPath("content").description("????????? ????????? ??????"),
                                subsectionWithPath("pageInfo").description("????????? ????????? ????????? ????????? ??????")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("id").type(NUMBER).description("????????? ID"),
                                fieldWithPath("author.id").type(NUMBER).description("????????? ID"),
                                fieldWithPath("author.name").type(STRING).description("????????? ??????"),
                                fieldWithPath("title").type(STRING).description("????????? ??????"),
                                fieldWithPath("techs").type(ARRAY).description("?????? ?????? ??????"),
                                fieldWithPath("createdDate").type(STRING).description("????????????")
                        ),
                        responseFields(
                                beneathPath("pageInfo").withSubsectionId("pageInfo"),
                                fieldWithPath("totalElements").type(NUMBER).description("??? ????????? ??????"),
                                fieldWithPath("totalPages").type(NUMBER).description("??? ????????? ??????"),
                                fieldWithPath("number").type(NUMBER).description("?????? ????????? ???"),
                                fieldWithPath("first").type(BOOLEAN).description("??? ????????? ??????"),
                                fieldWithPath("last").type(BOOLEAN).description("????????? ????????? ??????"),
                                fieldWithPath("numberOfElements").type(NUMBER).description("?????? ????????? ????????? ??????")
                        )
                ))
        ;
    }
}
