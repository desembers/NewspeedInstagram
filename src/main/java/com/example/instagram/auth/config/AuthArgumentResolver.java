package com.example.instagram.auth.config;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean haveAuth = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isValidType = parameter.getParameterType().equals(AuthUser.class);

        if (haveAuth != isValidType) {
            throw new IllegalArgumentException("Auth 와 AuthUser 가 함께 사용되어야 합니다.");
        }
        return haveAuth;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        if (userId != null) {
            return new AuthUser(userId);
        }
        return null;
    }
}
