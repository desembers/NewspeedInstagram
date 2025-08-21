package com.example.instagram.auth.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequest {

    private String userName;    // 카멜케이스 준수하도록 수정 및 통일
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")    // 비밀번호: 8~16자, 대소문자, 숫자, 특수문자를 최소 1개씩 포함
    private String password;
}
