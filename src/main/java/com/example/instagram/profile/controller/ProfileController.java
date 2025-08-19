package com.example.instagram.profile.controller;

import com.example.instagram.common.consts.Const;
import com.example.instagram.profile.dto.ProfileDtos;
import com.example.instagram.profile.dto.request.ProfileSaveRequestDto;
import com.example.instagram.profile.dto.request.ProfileUpdateRequestDto;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // POST /users/me/profiles : 프로필 생성
    // 사용자 프로필 최초 생성 : URI 를 "/users/me/profiles" 로 하는 이유는 인증 주체와 강하게 결합되었다는 의미.
    @PostMapping("/users/me/profiles")
    public ResponseEntity<ProfileResponseDto> create(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,         // 세션에서 인증 사용자 식별자 주입
            @Valid @RequestBody ProfileSaveRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.create(userId, dto));
    }

    ///  특정 유저의 프로필 조회
    // 공개 프로필 조회는 userId 경로 변수로

    /// version 1
//    // GET /users/{userId}/profiles : 특정 유저의 프로필 조회
//    @GetMapping("/users/{userId}/profiles")
//    public ResponseEntity<ProfileResponseDto> findOne(
//            @PathVariable Long userId
//    ) {
//        return ResponseEntity.ok(profileService.findByUserId(userId));
//    }

    /// version 2
    // GET /users/{usersid}/profiles  특정 유저의 프로필 조회
    @GetMapping("/users/{usersid}/profiles")
    public ResponseEntity<ProfileResponseDto> findProfile(          // 특정 유저의 프로필을 찾겠다!
            @PathVariable("usersid") Long userId                    // 왜: 경로 변수명과 정확히 일치시켜 매핑하므로 이름 불일치로 인한 400 오류 예방
    ) {
        return ResponseEntity.ok(profileService.findByUserId(userId));
    }

    // PATCH /users/me/profiles : 내 프로필 수정
    // 일부 필드만 수정 가능하도록 PATCH 채택
    @PatchMapping("/users/me/profiles")
    public ResponseEntity<ProfileResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody ProfileUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.update(userId, dto));
    }
}
