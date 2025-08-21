package com.example.instagram.user.repository;

import com.example.instagram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {     // CRUD + 페이징/정렬 기본 제공
    boolean existsByEmail(String email);                                // 회원가입 시 이메일 중복 빠른 검증
    boolean existsByUserName(String userName);                          // 사용자명 중복 검증
    Optional<User> findByEmail(String email);                           // 로그인/인증 시 이메일로 조회

    boolean existsByEmailAndDeletedFalse(String email);                 // Soft Delete 계정 제외하고 이메일 중복 체크
    Optional<User> findByEmailAndDeletedFalse(String email);            // Soft Delete 계정 제외 이메일 조회

}
