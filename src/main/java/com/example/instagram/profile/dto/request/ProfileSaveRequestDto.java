package com.example.instagram.profile.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProfileSaveRequestDto {

    @Size(max = 50, message = "닉네임은 최대 50자입니다.")        // DB 길이 제한과 일치화
    private String displayName;

    private String bio;                                       // @Lob 으로 저장되므로 길이 제한은 DB가 처리

    @Size(max = 255, message = "웹사이트 URL은 255자 이내입니다.")
    @Pattern(regexp = "^$|^https?://.+", message = "웹사이트는 http(s):// 로 시작해야 합니다.")
    private String website;                                   // 링크 안전성과 표준화(스키마 누락 방지)

    @Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일 입력 시, yyyy-MM-dd 형식이어야 합니다.")
    private String birthdate;                                 // ISO-8601 문자열(yyyy-MM-dd)로 받기 → 컨트롤러에서 LocalDate로 파싱되도록 유지
}