package com.example.instagram.comment.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.profile.entity.Profile;
import com.example.instagram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")    // 단수로 통일
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsfeed_id")    // 단수로 통일
    private NewsFeed newsFeed;

    public Comment(NewsFeed newsfeed, User user, String text) {
        this.newsFeed = newsfeed;
        this.user = user;
        this.text = text;
    }

    public void update(String text) {
        this.text = text;
    }


}
