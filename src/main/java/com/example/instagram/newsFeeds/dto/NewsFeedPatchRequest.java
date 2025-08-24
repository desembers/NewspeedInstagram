package com.example.instagram.newsFeeds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewsFeedPatchRequest {

    @NotBlank(message = "게시글 내용은 비어있을 수 없습니다.")
    @Size(max = 200, message = "게시글 내용은 200자 이하로 입력해주세요.")
    private String content;
}
