package com.example.instagram.user.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.common.consts.Const;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.profile.service.ProfileService;
import com.example.instagram.user.dto.request.UserSaveRequestDto;
import com.example.instagram.user.dto.request.UserUpdateRequestDto;
import com.example.instagram.user.dto.response.UserResponseDto;
import com.example.instagram.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                  // JSON API 컨트롤러
@RequiredArgsConstructor            // 의존성(서비스) 생성자 주입
@RequestMapping("/users")
public class UserController {   //test

    private final UserService userService;
    private final ProfileService profileService;

//    /// 회원가입 USERS 리소스 생성
//    /// 사용자 인증 회원가입 - "/auth/signup"
//    @PostMapping("/signup")
//    public ResponseEntity<UserResponseDto> signup(
//            @Valid @RequestBody UserSaveRequestDto dto) {         // @Valid: DTO의 Bean Validation을 트리거
//        return ResponseEntity.ok(userService.save(dto));
//    }

    // 유저 목록 조회(관리/테스트 용)
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 개별 유저 상세
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.findOne(userId));
    }

    /// 프로필 조회: GET /users/{usersId}/profiles
    @GetMapping("/{userId}/profiles")
    public ResponseEntity<ProfileResponseDto> findProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(profileService.findByUserId(userId));
    }

    // 본인 정보 수정(전체 교체 의미로 PUT 선택)
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> update(
            @Auth AuthUser authUser,
            @Valid @RequestBody UserUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(authUser.getId(), dto));
    }

//    // 본인 계정 삭제
//    @DeleteMapping("/me")
//    public ResponseEntity<Void> delete(
//
//            // 세션에 저장된 로그인 사용자 식별자 사용
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId
//    ) {
//        userService.deleteById(userId);
//        return ResponseEntity.ok().build();
//    }
}
