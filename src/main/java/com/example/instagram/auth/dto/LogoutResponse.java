package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class LogoutResponse {

    private Long id;
    private String email;

    public LogoutResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
