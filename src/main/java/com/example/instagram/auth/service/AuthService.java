package com.example.instagram.auth.service;

import com.example.instagram.auth.JwtTokenProvider;
import com.example.instagram.auth.dto.LoginRequest;
import com.example.instagram.auth.dto.LoginResponse;
import com.example.instagram.auth.dto.SignupRequest;
import com.example.instagram.auth.dto.SignupResponse;
import com.example.instagram.common.config.PasswordEncoder;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // 이메일 중복 여부 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 생성
        User user = new User(request.getEmail(), encodedPassword, request.getUserName());
        userRepository.save(user);
        return new SignupResponse(user.getId(), user.getEmail(), user.getUserName());
    }

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtTokenProvider.getAccessToken(user.getId());

        return new LoginResponse(user.getId(), user.getEmail(), accessToken);
    }
}
