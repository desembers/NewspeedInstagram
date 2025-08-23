package com.example.instagram.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor    // 파라미터로 AuthUser 자동 주입시 생성자 및 resolveArgument에서 객체 필요
// 인증된 사용자 정보 전용 DTO
public class AuthUser {

    private final Long id;
}

/**
 * @RequiredArgsConstructor 이 들어가는 경우 필요한 의존성
 * build.gradle에 lombok의 compileOnly + annotationProcessor가 있어야 함.
 * annotationProcessor 'org.projectlombok:lombok'
 */