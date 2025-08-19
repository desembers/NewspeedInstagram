package com.example.instagram.newsFeeds.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String name;

    public UserResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
