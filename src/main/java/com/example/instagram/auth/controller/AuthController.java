package com.example.instagram.auth.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.*;
import com.example.instagram.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request, BindingResult result
    ) {
        if (result.hasErrors()) {   // 유효성 검사 실패 시 에러코드 400 반환
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SignupResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);    // HTTP 코드 201
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        authService.logout(auth);
        return ResponseEntity.noContent().build();    // 코드 204
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @Auth AuthUser authUser,    // 토큰에서 추된 사용자 ID를 자동으로 주입받음
            @Valid @RequestBody WithdrawRequest request
    ) {
        authService.withdraw(authUser.getId(), request);    // authUser.getId()를 통해 인증된 사용자 ID를 서비스로 전달
        return ResponseEntity.noContent().build();    // HTTP 코드 204
    }
}
