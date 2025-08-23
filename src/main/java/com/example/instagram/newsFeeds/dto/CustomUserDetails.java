package com.example.instagram.newsFeeds.dto;

import com.example.instagram.user.entity.User;
import lombok.Data;

@Data
public class CustomUserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

}
