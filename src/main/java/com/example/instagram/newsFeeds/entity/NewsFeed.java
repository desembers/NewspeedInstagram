package com.example.instagram.newsFeeds.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 정적 팩토리 메서드
    public static NewsFeed of(String content, User user) {
        NewsFeed newsFeed = new NewsFeed();
        newsFeed.content = content;
        newsFeed.user = user;
        return newsFeed;
    }

    public void updateNewsFeed(String content){
        this.content=content;
    }

    /* Soft Delete 이므로 FK 관계 고려할 필요 X
    //FK 제약때문에 게시물 삭제 안될 시 사용
    public void removeUser() {
        this.user = null;
    }*/
}
