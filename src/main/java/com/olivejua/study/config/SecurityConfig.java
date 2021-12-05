package com.olivejua.study.config;

import com.olivejua.study.domain.user.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**", "/h2-console/**", "/resource/photo_upload/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .antMatchers("/", "/api/users/signin", "/api/users/signup").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/study-recruitment/posts",
                        "/api/place-recommendation/posts",
                        "/api/question/posts").permitAll()
                .antMatchers("/api/study-recruitment/posts/**",
                        "/api/place-recommendation/posts/**",
                        "/api/question/posts/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
        ;
    }
}
