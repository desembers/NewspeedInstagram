package com.example.instagram.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    // 응답 전용 불변 DTO로 엔티티 누출 방지 + 직렬화 안정성
    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponseDto(Long id,
                           String userName, String email,
                           LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
