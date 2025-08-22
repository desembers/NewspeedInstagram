package com.example.instagram.comment.entity;

import com.example.instagram.common.entity.BaseEntity;
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
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profiles_id")
    private Profile profile;

    public Comment(Profile profile,User user, String text) {
        this.text = text;
    }


    public void update(String text) {
        this.text = text;
    }

}
