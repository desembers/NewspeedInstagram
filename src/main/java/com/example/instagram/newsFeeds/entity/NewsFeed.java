package com.example.instagram.newsFeeds.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NewsFeed extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = true)
    private User user;

    public NewsFeed(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public void updateNewsFeed(String content){
        this.content=content;
    }
}