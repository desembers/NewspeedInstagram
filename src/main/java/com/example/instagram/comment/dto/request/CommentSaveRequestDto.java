package com.example.instagram.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
     @NotBlank(message = "댓글을 입력해주세요.")
     private String text;

     public CommentSaveRequestDto(String text) {
         this.text = text;
     }
}
