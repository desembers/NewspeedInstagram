package com.example.instagram.follow.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.follow.dto.FollowRequest;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.service.FollowService;
import com.example.instagram.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    public ResponseEntity<Void> follow(
            @Auth AuthUser authUser,
            @Valid @RequestBody FollowRequest dto) {
        followService.follow(authUser.getId(), dto.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팔로잉
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowingList(
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(followService.following.authUser);
    }

    //팔로워
    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponse>> getFollowerList(
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(followService.followers.authUser);
    }


    //언팔
    @DeleteMapping("/{followingUserId}")
    public ResponseEntity<Void> deleteFollow(
            @Auth AuthUser authUser,
            @PathVariable String frindName){
        followService.deleteFollow(fromUser, toUser);
        return ResponseEntity.noContent().build();
    }
}