package com.example.instagram.auth.repository;

import com.example.instagram.auth.entity.LogoutToken;
import org.springframework.data.jpa.repository.JpaRepository;

// 블랙리스트(더 이상 사용하면 안 되는 토큰 모음)
public interface LogoutTokenRepository extends JpaRepository<LogoutToken, String> {
}
