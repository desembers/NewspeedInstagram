package com.example.instagram.auth.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.*;
import com.example.instagram.auth.service.AuthService;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup(
            @RequestBody SignupRequest request
    ) {
        SignupResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        authService.logout(auth);
        return ResponseEntity.ok().build();
    }

    // 회원탈퇴
    @DeleteMapping("/auth/withdraw")
    public ResponseEntity<Void> withdraw(
            @Auth AuthUser authUser,    // 토큰에서 추된 사용자 ID를 자동으로 주입받음
            @RequestBody WithdrawRequest request
    ) {
        authService.withdraw(authUser.getId(), request);    // authUser.getId()를 통해 인증된 사용자 ID를 서비스로 전달
        return ResponseEntity.ok().build();
    }
}
