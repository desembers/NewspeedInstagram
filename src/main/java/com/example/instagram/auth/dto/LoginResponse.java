package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long id;
    private final String email;
    private final String accessToken;
//    private String refreshToken; -> 추후 작업

    public LoginResponse(Long id, String email, String accessToken) {
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
    }
}
