package com.example.instagram.follow.service;

import com.example.instagram.follow.dto.FollowRequest;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.entity.Follow;
import com.example.instagram.follow.repository.FollowRepository;
import com.example.instagram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(User fromUser, User toUser) {
    }
}
