package com.example.instagram.newsFeeds.dto;

import com.example.instagram.user.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
public class CustomUserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

}
