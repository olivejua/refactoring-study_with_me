package com.olivejua.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.config.auth.SecurityConfig;
import com.olivejua.study.service.UserService;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class,
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void signupTest() throws Exception {
        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .name("newUser1")
                .email("newUser1@gmail.com")
                .socialCode("google")
                .build();

        UserSignupResponseDto responseDto = new UserSignupResponseDto(requestDto.toEntity());

        when(userService.signUp(requestDto)).thenReturn(responseDto);

        mvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAllTheNamesTest() throws Exception {
        List<String> response = new ArrayList<>();
        response.add("sample username1");
        response.add("sample username2");
        response.add("sample username3");

        when(userService.findAllNames()).thenReturn(response);

        mvc.perform(get("/user/getTheNames")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void changeNameTest() throws Exception {
        String changedName = "changed username1";

        doNothing().when(userService).changeProfile(anyLong(), anyString());

        mvc.perform(put("/user/name/{changedName}", changedName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
