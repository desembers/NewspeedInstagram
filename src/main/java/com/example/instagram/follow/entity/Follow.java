package com.example.instagram.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"from_user", "to_user"}
        )
)

public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //팔로우 함
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;
    //팔로우 받음
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Follow(User fromUser, User toUser) {

        if (Objects.equals(fromUser.getName(), toUser.getName())) {
            throw new IllegalArgumentException("자신의 계정을 팔로우 할 수 없습니다.");
        }

        this.fromUser = fromUser;
        this.toUser = toUser;
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
