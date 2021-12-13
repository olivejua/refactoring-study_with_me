package com.olivejua.study.config;

import com.olivejua.study.auth.handler.CustomAuthenticationFailureHandler;
import com.olivejua.study.auth.handler.CustomAuthenticationSuccessHandler;
import com.olivejua.study.auth.CustomOAuth2UserService;
import com.olivejua.study.auth.JwtAuthenticationFilter;
import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.domain.user.Role;
import com.olivejua.study.auth.handler.JwtAccessDeniedHandler;
import com.olivejua.study.auth.handler.JwtAuthenticationExceptionHandler;
import com.olivejua.study.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static com.olivejua.study.utils.ApiUrlPaths.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler;

    private final AuthTokenRepository authTokenRepository;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/static/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
        ;

        http.exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationExceptionHandler)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        ;

        http.oauth2Login()
                .loginPage(USERS+Users.SIGN_IN)
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider, authTokenRepository))
                .failureHandler(new CustomAuthenticationFailureHandler(jwtTokenProvider))
                .permitAll()
        ;

        http.addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider), LogoutFilter.class) //실질적인 인증과정이 일어나기 직전 위치
        ;

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        STUDY_RECRUITMENT+POSTS,
                        PLACES_RECOMMENDATION+POSTS,
                        QUESTION+POSTS).permitAll()
                .antMatchers(STUDY_RECRUITMENT+POSTS+"/**",
                    PLACES_RECOMMENDATION+POSTS+"/**",
                    QUESTION+POSTS+"/**").hasRole(Role.USER.name())
                .antMatchers("/", USERS+Users.SIGN_IN, USERS+Users.SIGN_UP).permitAll()
                .anyRequest().authenticated()
        ;

    }
}
