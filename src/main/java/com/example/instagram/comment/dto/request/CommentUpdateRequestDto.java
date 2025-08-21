package com.example.instagram.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    @NotBlank(message = "댓글을 입력해주세요")
    private String text;

    public CommentUpdateRequestDto(String text) {
        this.text = text;
    }
}
