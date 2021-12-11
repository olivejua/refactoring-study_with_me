package com.olivejua.study.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.olivejua.study.utils.ApiUrlPaths.USERS;
import static com.olivejua.study.utils.ApiUrlPaths.Users;

@RequestMapping(USERS)
@RequiredArgsConstructor
@RestController
public class LoginController {

    @GetMapping(Users.SIGN_UP)
    public String signUp() {
        return "<h1>Register</h1>";
    }

    @GetMapping(Users.SIGN_IN)
    public String signIn() {
        return "<h1>Login</h1>" +
                "<a href=\"/oauth2/authorization/naver\">naver login</a><br/>" +
                "<a href=\"/oauth2/authorization/google\">google login</a>";
    }
}
