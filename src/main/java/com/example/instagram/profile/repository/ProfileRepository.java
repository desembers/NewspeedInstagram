package com.example.instagram.profile.repository;

import com.example.instagram.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // PK가 user_id 이므로 타입은 LONG

    // 프로필 조회 및 검증 - 사용자 Id 프로필 조회
    boolean existsByUserId(Long userId);       // 중복 생성 방지
    Optional<Profile> findByUserId(Long userId);

    // Soft Delete 적용
    boolean existsByUserIdAndDeletedFalse(Long userId);
    Optional<Profile> findByUserIdAndDeletedFalse(Long userId);
}