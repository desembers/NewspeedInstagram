package com.example.instagram.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor    // 파라미터로 AuthUser 자동 주입시 생성자 및 resolveArgument에서 객체 필요
// 인증된 사용자 정보 전용 DTO
public class AuthUser {

    private final Long id;
}
