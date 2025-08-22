package com.example.instagram.follow.service;

import com.example.instagram.follow.repository.FollowRepository;
import com.example.instagram.user.repository.UserRepository;
import com.example.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(Long fromUser, Long toUser) {
        if (fromUser == null || toUser == null) {
            throw new IllegalArgumentException("팔로우 대상이 유효하지 않습니다.");
        }

        if (fromUser.equals(toUser)) {
            throw new IllegalArgumentException("본인의 계정은 팔로우 할 수 없습니다");
        }
        //
        if (followRepository.existsByFromIdAndToId(fromUser, toUser)) {
            return;
        }
        follow(fromUser, toUser);
    }

    @Transactional
    public void deleteFollow(Long fromUser, Long toUser) {
        if (fromUser == null || toUser == null) {
            throw new IllegalArgumentException("팔로우 대상이 유효하지 않습니다.");
        }
        followRepository.deletByFromIdAndToId(fromUser, toUser);

    }
//    User fromUser = userRepository.findById(fromUser);
//    User toUser = userService.findOne(dto.getUserName());
//
//    boolean exists = followRepository.existsByFromIdAndToId(fromUser.get);
//    if (!exists) return null;
//
//    followRepository.save(new Follow(fromUser, fromId));
//    }
}
