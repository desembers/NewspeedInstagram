package com.example.instagram.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private long id;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(long id, long userId, String text, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
