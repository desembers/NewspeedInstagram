package com.example.instagram.newsFeeds.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class NewsFeedGetResponse {
    private final Long id;
    private final Long authorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public NewsFeedGetResponse(
            Long id,
            Long authorId,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt)
    {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
