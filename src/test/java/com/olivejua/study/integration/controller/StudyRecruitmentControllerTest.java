package com.olivejua.study.integration.controller;

import com.olivejua.study.auth.JwtTokenProvider;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UploadService uploadService;

    @Autowired
    private StudyRecruitmentRepository studyRecruitmentRepository;

    private static final String DIRECT = "study-recruitment/";

    private User testUser;
    private String accessToken;

    @BeforeEach
    void setup() {
        testUser = userFactory.user();
        accessToken = getAccessToken(testUser.getId());
    }

    @Test
    @DisplayName("스터디 모집 게시글을 첨부이미와 같이 작성한다")
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
    @DisplayName("스터디 모집 게시글을 작성한다")
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
                                parameterWithName("title").description("게시글 제목"),
                                parameterWithName("techs").description("스터디에서 사용할 기술 스택"),
                                parameterWithName("meetingPlace").description("만남 장소 (지역)"),
                                parameterWithName("startDate").description("스터디 시작일자"),
                                parameterWithName("endDate").description("스터디 종료일자"),
                                parameterWithName("capacity").description("최대 인원 수"),
                                parameterWithName("explanation").description("추가 설명")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("성공 처리 여부")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("스터디 모집 게시글을 작성할 때 유효성 검사가 실패한다")
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
    @DisplayName("스터디 모집 게시글을 수정한다")
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
                                parameterWithName("title").description("게시글 제목"),
                                parameterWithName("techs").description("스터디에서 사용할 기술 스택"),
                                parameterWithName("meetingPlace").description("만남 장소 (지역)"),
                                parameterWithName("startDate").description("스터디 시작일자"),
                                parameterWithName("endDate").description("스터디 종료일자"),
                                parameterWithName("capacity").description("최대 인원 수"),
                                parameterWithName("explanation").description("추가 설명")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("성공 처리 여부")
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
    @DisplayName("스터디 모집 게시글을 삭제한다")
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
                                fieldWithPath("success").type(BOOLEAN).description("성공 처리 여부")
                        )
                ));
    }

    @Test
    @DisplayName("하나의 스터디 모집 게시글 가져온다")
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
                                fieldWithPath("success").type(BOOLEAN).description("성공 처리 여부"),
                                subsectionWithPath("content").description("조회한 게시글 내용")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("post.id").type(NUMBER).description("게시글 ID"),
                                fieldWithPath("post.author.id").type(NUMBER).description("작성자 ID"),
                                fieldWithPath("post.author.name").type(STRING).description("작성자 이름"),
                                fieldWithPath("post.title").type(STRING).description("게시글 제목"),
                                fieldWithPath("post.techs").type(ARRAY).description("기술 스택 목록"),
                                fieldWithPath("post.meetingPlace").type(STRING).description("만남 장소 (지역)"),
                                fieldWithPath("post.startDate").type(STRING).description("스터디 시작일자"),
                                fieldWithPath("post.endDate").type(STRING).description("스터디 종료일자"),
                                fieldWithPath("post.capacity").type(NUMBER).description("최대 인원 수"),
                                fieldWithPath("post.explanation").type(STRING).description("추가 설명"),
                                fieldWithPath("post.createdDate").type(STRING).description("작성일자")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("스터디 모집 게시글 목록을 가져온다")
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
                                fieldWithPath("success").type(BOOLEAN).description("성공 처리 여부"),
                                subsectionWithPath("content").description("요청한 컨텐츠 내용"),
                                subsectionWithPath("pageInfo").description("요청한 컨텐츠 목록의 페이징 정보")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("id").type(NUMBER).description("게시글 ID"),
                                fieldWithPath("author.id").type(NUMBER).description("작성자 ID"),
                                fieldWithPath("author.name").type(STRING).description("작성자 이름"),
                                fieldWithPath("title").type(STRING).description("게시글 제목"),
                                fieldWithPath("techs").type(ARRAY).description("기술 스택 목록"),
                                fieldWithPath("createdDate").type(STRING).description("작성일자")
                        ),
                        responseFields(
                                beneathPath("pageInfo").withSubsectionId("pageInfo"),
                                fieldWithPath("totalElements").type(NUMBER).description("총 컨텐츠 개수"),
                                fieldWithPath("totalPages").type(NUMBER).description("총 페이지 개수"),
                                fieldWithPath("number").type(NUMBER).description("현재 페이지 수"),
                                fieldWithPath("first").type(BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지 컨텐츠 개수")
                        )
                ))
        ;
    }
}
