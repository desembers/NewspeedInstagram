package com.example.instagram.auth.entity;

import com.example.instagram.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "logout_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutToken extends BaseEntity {

    @Id
    private String token;

    private LocalDateTime expiredAt;

    private LogoutToken(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public static LogoutToken create(String token, LocalDateTime expiredAt) {
        return new LogoutToken(token, expiredAt);
    }
}
