package com.example.instagram.follow.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.follow.dto.FollowRequest;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.service.FollowService;
import com.example.instagram.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    //팔로우
    @PostMapping
    public ResponseEntity<FollowResponse> follow(
            @Auth AuthUser authUser,
            @Valid @RequestBody FollowRequest dto
    ) {
        FollowResponse followResponse = followService.follow(authUser.getId(), dto.getToUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(followResponse);
    }

    //팔로잉
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowingList(
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(followService.followings(authUser.getId()));
    }

    //팔로워
    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponse>> getFollowerList(
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(followService.followers(authUser.getId()));
    }

    //언팔
    @DeleteMapping("/{unfollowId:\\d+}")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<Void> deleteFollow(@Auth AuthUser authUser, @PathVariable Long unfollowId) {
        followService.deleteFollow(authUser, unfollowId);
        return ResponseEntity.noContent().build();
    }
}
