package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final Long id;
    private final String email;
    private final String userName;

    public SignupResponse(Long id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }
}
