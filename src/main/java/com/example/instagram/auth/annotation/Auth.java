package com.example.instagram.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface Auth {    // @interface: 자바에서 어노테이션(Annotation) 정의
}

/**
 * 핵심은 JWT 필터가 AuthUser를 HttpServletRequest에 심고, 리졸버가 그걸 컨트롤러 파라미터로 꺼내는 흐름
 * */