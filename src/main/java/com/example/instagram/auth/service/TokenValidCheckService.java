package com.example.instagram.auth.service;

import com.example.instagram.auth.repository.LogoutTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidCheckService {

    private final LogoutTokenRepository logoutTokenRepository;

    public boolean isValid(String token) {
        return !logoutTokenRepository.existsById(token);    // 블랙리스트에 토큰이 없으면, 토큰 유효
    }
}
