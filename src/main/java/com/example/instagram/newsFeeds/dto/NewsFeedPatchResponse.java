package com.example.instagram.newsFeeds.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsFeedPatchResponse {
    private final Long id;
    private final Long authorId;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;

    public NewsFeedPatchResponse(Long id, Long authorId, String content, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }
}
