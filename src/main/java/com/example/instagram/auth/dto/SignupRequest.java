package com.example.instagram.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    @Size(min = 2, max = 30, message = "사용자 이름은 2~30자입니다.")
    private String userName;    // 카멜케이스 준수하도록 수정 및 통일

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")    // 비밀번호: 8~16자, 대소문자, 숫자, 특수문자를 최소 1개씩 포함
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
