package com.example.instagram.user.controller;

import com.example.instagram.common.consts.Const;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
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
public class UserController {

    private final UserService userService;

    /// 회원가입 USERS 리소스 생성
    /// 사용자 인증 회원가입 - "/auth/signup"
    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signup(
            @Valid @RequestBody UserSaveRequestDto dto) {         // @Valid: DTO의 Bean Validation을 트리거
        return ResponseEntity.ok(userService.save(dto));
    }

    // 유저 목록 조회(관리/테스트 용)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 개별 유저 상세
    @GetMapping("/users/{usersid}")
    public ResponseEntity<UserResponseDto> findOne(
            @PathVariable Long userid) {                            // 경로 플레이스홀더 이름이 'usersid'이므로
                                                                    // 명시적으로 매핑해 400/404 매핑 오류를 방지
        return ResponseEntity.ok(userService.findOne(userid));      // 컨트롤러는 조회 위임만, 변환/검증은 서비스
    }

    /// 프로필 조회: GET /users/{usersid}/profiles  수정!!  version
//    @GetMapping("/users/{usersid}/profiles")
//    public ResponseEntity<ProfileResponseDto> findProfile(
//            @PathVariable("usersid") Long userId // 왜: 경로 변수명과 정확히 일치시켜 매핑
//    ) {
//        return ResponseEntity.ok(profileService.findByUserId(userId));
//    }

    // 본인 정보 수정(전체 교체 의미로 PUT 선택)
    @PutMapping("/users/me")
    public ResponseEntity<UserResponseDto> update(

            // 세션에 저장된 로그인 사용자 식별자 사용
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,

            @Valid @RequestBody UserUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(userId, dto));
    }

    // 본인 계정 삭제
    @DeleteMapping("/users/me")
    public ResponseEntity<Void> delete(

            // 세션에 저장된 로그인 사용자 식별자 사용
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
