package com.example.instagram.follow.controller;

import com.example.instagram.follow.dto.FollowRequest;
import com.example.instagram.follow.dto.FollowResponse;
import com.example.instagram.follow.service.FollowService;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.service.UserService;
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
    public ResponseEntity<Void> follow(Authentication authentication, @RequestBody FollowRequest dto) {
        Long fromUser = userService.findOne(authentication.getName());
        User toUser = userService.findOne(dto.getUserName());
        followService.follow(fromUser, toUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //팔로잉
    @GetMapping("/{userName}/following")
    public ResponseEntity<List<FollowResponse>> getFollowingList(@PathVariable String userName, Authentication auth) {
        User progileOwner = userService.findOne(userName);
        User request = userService.findOne(auth.getName());
        return ResponseEntity.ok(following.followingList(profilOwner, request));
    }

    //팔로워
    @GetMapping("/{userName}/followers")
    public ResponseEntity<List<FollowResponse>> getFollowingList(@PathVariable String userName, Authentication auth) {
        User progileOwner = userService.findOne(userName);
        User request = userService.findOne(auth.getName());
        return ResponseEntity.ok(following.followingList(profilOwner, request));

    //언팔
    @DeleteMapping("/{frandName}")
    public ResponseEntity<Void> deleteFollow(Authentication authentication, @PathVariable String frindName){
            Long fromUser = userService.findOne(authentication.getName());
            User toUser = userService.findOne(dto.getUserName());
            followService.follow(fromUser, toUser);
            return ResponseEntity.noContent().build();
        }
}
