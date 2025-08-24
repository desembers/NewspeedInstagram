package com.example.instagram.profile.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.profile.dto.request.ProfileSaveRequestDto;
import com.example.instagram.profile.dto.request.ProfileUpdateRequestDto;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")                                               // preFix 기능
public class ProfileController {

    private final ProfileService profileService;

    // POST /users/me/profiles : 프로필 생성
    // 사용자 프로필 최초 생성 : URI 를 "/users/me/profiles" 로 하는 이유는 인증 주체와 강하게 결합되었다는 의미.
    @PostMapping("/me/profiles")
    public ResponseEntity<ProfileResponseDto> create(
            @Auth AuthUser authUser,
            @Valid @RequestBody ProfileSaveRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.create(authUser.getId(), dto));
    }

    // GET /users/{userId}/profiles  특정 유저의 프로필 조회
    @GetMapping("/{userId}/profiles")
    public ResponseEntity<ProfileResponseDto> findProfile(          // 특정 유저의 프로필을 찾겠다!
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(profileService.findByUserId(userId));
    }

    @GetMapping("/me/profiles")
    public ResponseEntity<ProfileResponseDto> myProfile(          // 특정 유저의 프로필을 찾겠다!
        @Auth AuthUser authUser
    ) {
        return ResponseEntity.ok(profileService.findByUserId(authUser.getId()));
    }

    // PATCH /users/me/profiles : 내 프로필 수정
    // 일부 필드만 수정 가능하도록 PATCH 채택
    @PatchMapping("/me/profiles")
    public ResponseEntity<ProfileResponseDto> update(
            @Auth AuthUser authUser,
            @Valid @RequestBody ProfileUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.update(authUser.getId(), dto));
    }

    // DELETE /users/me/profiles : 내 프로필 삭제
    @DeleteMapping("/me/profiles")
    public ResponseEntity<Void> deleteMyProfile(
            @Auth AuthUser authUser
    ) {
        profileService.deleteByUserId(authUser.getId());  // 사용자 userId로 삭제
        return ResponseEntity.noContent().build();        // 204 No Content 응답
    }
}
