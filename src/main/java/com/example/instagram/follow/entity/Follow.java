package com.example.instagram.follow.entity;

import com.example.instagram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"from_user_id", "to_user_id"}
        )
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //팔로우 함
    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    //팔로우 받음
    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Follow(User fromUser, User toUser) {

        if (fromUser.getId().equals(toUser.getId())) {
            throw new IllegalArgumentException("자신의 계정을 팔로우 할 수 없습니다.");
        }
        this.fromUser = fromUser; // 팔로우보내는 사람
        this.toUser = toUser; //팔로우 받는 사람
    }

    public static Follow of(User fromUser, User toUser) {
        return new Follow(fromUser, toUser);
    }

    @PrePersist
    void onCreate() {
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
    }
}
