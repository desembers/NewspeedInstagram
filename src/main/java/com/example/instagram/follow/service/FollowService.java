package com.example.instagram.follow.service;

import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.entity.Follow;
import com.example.instagram.follow.exception.DuplicatedFollowException;
import com.example.instagram.follow.repository.FollowRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponse follow(Long fromUserId, Long toUserId) {
        
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("본인의 계정은 팔로우 할 수 없습니다");
        }
        if (followRepository.existsByFromUser_IdAndToUser_Id(fromUserId, toUserId)) {
            throw new DuplicatedFollowException("이미 팔로우 된 계정입니다.", HttpStatus.BAD_REQUEST);
        }

        User fromUser = userRepository.findById(fromUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        User toUser = userRepository.findById(toUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        Follow follow = followRepository.save(Follow.of(fromUser, toUser));
        FollowResponse followResponse = new FollowResponse(follow.getId(), toUserId, toUser.getUserName());
        return followResponse;
    }

    @Transactional
    public void deleteFollow(AuthUser authUser, Long id) {
        Follow follow = followRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        if (follow.getToUser().getId().equals(authUser.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }
        followRepository.deleteById(id);
    }

    public List<FollowResponse> followings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        List<Follow> follows = followRepository.findAllByFromUser(user);
        List<FollowResponse> result = new ArrayList<>();
        for(int i = 0; i < follows.size(); i++) {
            Follow follow = follows.get(i);
            FollowResponse followResponse = new FollowResponse(follow.getId(),follow.getToUser().getId(), follow.getToUser().getUserName());
            result.add(followResponse);
        }
        return result;
    }

    public List<FollowResponse> followers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        List<Follow> follows = followRepository.findAllByToUser(user);
        List<FollowResponse> result = new ArrayList<>();
        for(int i = 0; i < follows.size(); i++) {
            Follow follow = follows.get(i);
            FollowResponse followResponse = new FollowResponse(follow.getId(),follow.getFromUser().getId(), follow.getFromUser().getUserName());
            result.add(followResponse);
        }
        return result;
    }
}
