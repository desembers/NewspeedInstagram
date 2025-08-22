package com.example.instagram.follow.service;

import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.entity.Follow;
import com.example.instagram.follow.repository.FollowRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import com.example.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long fromUser, Long toUser) {
//        if (fromUser == null || toUser == null) {
//            throw new IllegalArgumentException("팔로우 대상이 유효하지 않습니다.");
//        }

        if (fromUser.equals(toUser)) {
            throw new IllegalArgumentException("본인의 계정은 팔로우 할 수 없습니다");
        }
        //
        if (followRepository.existsByFromIdAndToId(fromUser, toUser)) {
            throw new DuplicateFollowException("중복된 팔로우입니다.", HttpStatus.BAD_REQUEST)
        }
        followRepository.save(Follow.of(follow(fromUser, toUser)));
    }

    @Transactional
    public void deleteFollow(Long fromUser, Long toUser) {
        Foll
        User user = userRepository.findById(fromUser).orElseThrow();
//        if (fromUser == null || toUser == null) {           //
//            throw new IllegalArgumentException("팔로우 대상이 유효하지 않습니다.");
//        }
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

    public List<FollowResponse> followins(AuthUser authUser) {
        User user = userRepository
    }
}