package com.example.instagram.profile.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {

    @Size(max = 50, message = "닉네임은 최대 50자입니다.")
    private String displayName;

    private String bio;

    @Size(max = 255, message = "웹사이트 URL은 255자 이내입니다.")
    @Pattern(regexp = "^$|^https?://.+", message = "웹사이트는 http(s):// 로 시작해야 합니다.")
    private String website;

    ///  문자열도 주의해보기 @Pattern
    private String birthdate;
}
