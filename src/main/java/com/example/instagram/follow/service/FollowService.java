package com.example.instagram.follow.service;

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
        if (fromUser == null || toUser == null) {
            throw new IllegalArgumentException("팔로우 대상이 유효하지 않습니다.");
        }

        if (fromUser.getId().equals(toUser.getId())) {
            throw new IllegalArgumentException("본인의 계정은 팔로우 할 수 없습니다");
        }
    }
    boolean exists = followRepository.existsByFromIdAndToId(fromUser.getId(toUser.getId()));
    if (exists) return;

    followRepository.save(new Follow(fromUser, fromId));
    }
}
