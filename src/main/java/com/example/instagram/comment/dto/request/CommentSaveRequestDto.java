package com.example.instagram.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
     @NotBlank(message = "댓글을 입력해주세요.")
     @Size(max = 100, message = "댓글 내용은 100자 이하로 입력해주세요.")
     private String text;

     public CommentSaveRequestDto(String text) {
         this.text = text;
     }
}
