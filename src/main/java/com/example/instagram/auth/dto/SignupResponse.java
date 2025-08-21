package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final Long id;
    private final String userName;
    private final String email;

    public SignupResponse(Long id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }
}
