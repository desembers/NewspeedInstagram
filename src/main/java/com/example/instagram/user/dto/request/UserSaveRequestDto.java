package com.example.instagram.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    @NotBlank(message = "사용자 이름은 필수입니다.")                       // 컨트롤러 @Valid와 함께 동작
    @Size(min = 2, max = 30, message = "사용자 이름은 2~30자입니다.")      // 도메인 규칙을 명확히
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 64, message = "비밀번호는 6~64자입니다.")        // 너무 짧은 비번 방지
    private String password;
}
