package com.example.instagram.follow.repository;

import com.example.instagram.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFromIdAndToId(Long fromId, Long toId);
    void deletByFromIdAndToId(Long fromId, Long toId);
}