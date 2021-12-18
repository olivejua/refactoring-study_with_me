package com.olivejua.study.web;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.auth.annotation.AppGuestUser;
import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.GuestUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.auth.AuthToken;
import com.olivejua.study.repository.AuthTokenRepository;
import com.olivejua.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static com.olivejua.study.utils.ApiUrlPaths.USERS;
import static com.olivejua.study.utils.ApiUrlPaths.Users;

@RequestMapping(USERS)
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;

    @GetMapping(Users.SIGN_UP)
    public String signUpPage() {
        return "<h1>Register</h1>";
    }

    @PostMapping(Users.SIGN_UP)
    public String signUp(@AppGuestUser GuestUser guestUser) {
        System.out.println(guestUser);

        return "success!";
    }

    @GetMapping(Users.SIGN_IN)
    public String signIn() {
        return "<h1>Login</h1>" +
                "<a href=\"/oauth2/authorization/naver\">naver login</a><br/>" +
                "<a href=\"/oauth2/authorization/google\">google login</a>";
    }

    @Transactional
    @PostMapping("/token")
    public String getToken(@AppLoginUser LoginUser loginUser, HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveToken(request);

        AuthToken token = authTokenRepository.findByUser(loginUser.getUser())
                .orElseThrow(() -> new IllegalArgumentException("유저의 토큰 정보를 찾을 수 없습니다."));

        boolean validate = token.isExpired(jwtTokenProvider) && token.equalsToken(refreshToken);
        if (validate) {
            String newRefreshToken = jwtTokenProvider.createRefreshTokenForUser(loginUser.getUserId());
            token.updateRefreshToken(newRefreshToken);

            return newRefreshToken;
        }

        return null; // 로그인 페이지로 이동
    }
}
