package com.example.instagram.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WithdrawRequest {

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
