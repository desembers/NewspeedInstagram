package com.example.instagram.user.repository;

import com.example.instagram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {     // CRUD + 페이징/정렬 기본 제공
    boolean existsByEmailAndDeletedFalse(String email);                 // Soft Delete 계정 제외하고 이메일 중복 체크
    Optional<User> findByEmailAndDeletedFalse(String email);            // Soft Delete 계정 제외 이메일 조회
}
