package com.example.instagram.newsFeeds.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsFeedSaveResponse {
    private final Long id;
    private final Long AuthorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public NewsFeedSaveResponse(Long id, Long authorId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        AuthorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
