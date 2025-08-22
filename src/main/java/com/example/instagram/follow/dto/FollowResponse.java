package com.example.instagram.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponse {

    private final Long id;
    private final Long userId;
    private final String userName;

    public FollowResponse(Long id, Long userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
}