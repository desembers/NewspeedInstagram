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
    @JoinColumn(name = "user_id")    // 단수로 통일
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil_id")    // 단수로 통일
    private Profile profile;

    public Comment(Profile profile,User user, String text) {
        this.text = text;
    }


    public void update(String text) {
        this.text = text;
    }

}
