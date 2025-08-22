package com.example.instagram.profile.repository;

import com.example.instagram.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // PK가 user_id 이므로 타입은 LONG

    // 프로필 조회 및 검증 - 사용자 Id 프로필 조회
    boolean existsByUserId(Long userId);       // 중복 생성 방지
    Optional<Profile> findByUserId(Long userId);

    // 프로필 삭제
    void deleteByUserId(Long userId);
}