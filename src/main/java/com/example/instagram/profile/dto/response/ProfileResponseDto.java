package com.example.instagram.profile.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ProfileResponseDto {

    ///  API 응답에서 필요한 값만 노출
    private final Long userId;
    private final String displayName;
    private final String bio;
    private final String website;
    private final LocalDate birthdate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProfileResponseDto(Long userId,
                              String displayName,
                              String bio,
                              String website,
                              LocalDate birthdate,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.bio = bio;
        this.website = website;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
