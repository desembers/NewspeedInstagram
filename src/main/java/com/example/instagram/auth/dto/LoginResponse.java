package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private Long id;
    private String email;

    public LoginResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
