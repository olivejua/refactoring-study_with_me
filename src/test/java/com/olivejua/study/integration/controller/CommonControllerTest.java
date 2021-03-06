package com.olivejua.study.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.common.docs.RestDocsConfiguration;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.integration.IntegrationTest;
import com.olivejua.study.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import({
        RestDocsConfiguration.class
})
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class CommonControllerTest extends IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected UploadService uploadService;

    protected User testUser;
    protected String accessToken;

    protected void setupUserAndAccessToken() {
        testUser = userFactory.user();
        accessToken = getAccessToken(testUser.getId());
    }

    protected String getAccessToken(Long userId) {
        return "Bearer " + jwtTokenProvider.createTokenForUser(userId);
    }

    protected User createTestUser() {
        return userFactory.user();
    }
}
