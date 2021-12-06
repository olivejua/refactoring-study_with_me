package com.olivejua.study.config;

import static com.olivejua.study.utils.ApiUrlPaths.*;

import com.olivejua.study.config.security.JwtAuthenticationFilter;
import com.olivejua.study.config.security.JwtTokenProvider;
import com.olivejua.study.domain.user.Role;
import com.olivejua.study.utils.ApiUrlPaths;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        http.authorizeRequests()
                .antMatchers("/", USERS+Users.SIGN_IN, USERS+Users.SIGN_UP).permitAll()
                .antMatchers(HttpMethod.GET,
                        STUDY_RECRUITMENT+POSTS,
                        PLACES_RECOMMENDATION+POSTS,
                        QUESTION+POSTS).permitAll()
                .antMatchers(STUDY_RECRUITMENT+POSTS+"/**",
                    PLACES_RECOMMENDATION+POSTS+"/**",
                    QUESTION+POSTS+"/**").hasRole(Role.USER.getKey())
                .anyRequest().authenticated()
        ;

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class)
        ;


    }
}
