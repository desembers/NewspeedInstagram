package com.example.instagram.auth.dto;

import lombok.Getter;

@Getter
public class SignupRequest {

    private String email;
    private String password;
    private String userName;    // 카멜케이스 준수하도록 수정 및 통일
}
