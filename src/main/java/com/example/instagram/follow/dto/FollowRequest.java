package com.example.instagram.follow.dto;

import lombok.Getter;

@Getter
public class FollowRequest {

    private String userName;
//    private Long userId;                /// 유저 id 키를 가져와서

    @NotNull
}