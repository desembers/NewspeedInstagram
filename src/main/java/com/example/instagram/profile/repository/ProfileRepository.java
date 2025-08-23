package com.example.instagram.profile.repository;

import com.example.instagram.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // PK가 user_id 이므로 타입은 LONG

    // Soft Delete 적용
    boolean existsByUserIdAndDeletedFalse(Long userId);
    Optional<Profile> findByUserIdAndDeletedFalse(Long userId);
}