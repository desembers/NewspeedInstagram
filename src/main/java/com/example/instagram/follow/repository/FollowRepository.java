package com.example.instagram.follow.repository;

import com.example.instagram.follow.entity.Follow;
import com.example.instagram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFromUser_IdAndToUser_Id(Long fromUser, Long toUser);
    List<Follow> findAllByFromUser(User fromUser);
    List<Follow> findAllByToUser(User toUser);
}
