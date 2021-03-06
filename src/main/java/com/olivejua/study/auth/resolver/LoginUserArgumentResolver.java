package com.olivejua.study.auth.resolver;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.AuthenticatedUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AppLoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                                                 NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();

        User loginUser = userService.findById(principal.getId());
        return new LoginUser(loginUser);
    }
}
