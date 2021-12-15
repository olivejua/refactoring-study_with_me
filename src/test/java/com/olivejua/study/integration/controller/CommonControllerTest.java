package com.olivejua.study.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.common.docs.RestDocsConfiguration;
import com.olivejua.study.integration.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

    protected String makeAccessToken(Long userId) {
        return "Bearer" + jwtTokenProvider.createTokenForUser(userId);
    }
}
