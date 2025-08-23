package com.example.instagram.user.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
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
public class UserController {

    private final UserService userService;

    // 유저 목록 조회(관리/테스트 용)
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> findOne(
            @Auth AuthUser authUser
    ) {
        return ResponseEntity.ok(userService.findOne(authUser.getId()));
    }

    // 특정 사용자 조회
    @GetMapping("/{userId:\\d+}")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<UserResponseDto> findSomeOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.findSomeOne(userId));
    }

    // 본인 정보 수정(일부 교체 의미로 Patch 선택)
    @PatchMapping("/me")
    public ResponseEntity<UserResponseDto> update(
            @Auth AuthUser authUser,
            @Valid @RequestBody UserUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(authUser.getId(), dto));
    }
}
