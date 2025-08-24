package com.example.instagram.newsFeeds.dto;

import com.example.instagram.comment.dto.response.CommentResponse;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NewsFeedGetResponse {
    private final Long id;
    private final Long authorId;
    private final String content;
    private final List<CommentResponse> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public NewsFeedGetResponse(
            Long id,
            Long authorId,
            String content,
            List<CommentResponse> comments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt)
    {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
