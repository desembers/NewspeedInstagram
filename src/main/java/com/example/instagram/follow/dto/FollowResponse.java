package com.example.instagram.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponse {

    private final Long id;
    private final String userName;

    public FollowResponse(Long id, String userName) {
        this.id = id;
        this.userName = userName;

    }
}
