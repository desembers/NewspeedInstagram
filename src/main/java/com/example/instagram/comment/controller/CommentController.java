package com.example.instagram.comment.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.comment.dto.request.CommentSaveRequestDto;
import com.example.instagram.comment.dto.request.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.service.CommentService;
import com.example.instagram.newsFeeds.dto.NewsFeedGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

      private final CommentService commentService;

      @PostMapping("/newsFeeds/{feedId}/comments")
      public ResponseEntity<CommentResponse> save(
              @Auth AuthUser authUser,
              @PathVariable Long feedId,
              @RequestBody CommentSaveRequestDto requestDto
              ) {
          return ResponseEntity.ok(commentService.save(authUser.getId(), feedId , requestDto));
      }

      @GetMapping("/users/{userId}/comments")
      public ResponseEntity<List<CommentResponse>> findId(
              @PathVariable long userId
      )  {
          return ResponseEntity.ok(commentService.findByUserId(userId));
      }

      @GetMapping("/newsFeeds/{newsFeedId}/comments")
      public ResponseEntity<List<CommentResponse>> findByNewsFeedId(
              @PathVariable Long newsFeedId
      ) {
            return ResponseEntity.ok(commentService.findByNewsFeedId(newsFeedId));
      }

      @PutMapping("/newsFeeds/comments/{commentId}")
      public ResponseEntity<CommentResponse> update(
              @Auth AuthUser authUser,
              @PathVariable long commentId,
              @RequestBody CommentUpdateRequestDto requestDto
      ) {
          return ResponseEntity.ok(commentService.update(authUser.getId(), commentId, requestDto));
      }

      @DeleteMapping("/newsFeeds/comments/{commentId}")
      public void delete(
              @Auth AuthUser authUser,
              @PathVariable long commentId
      ) {
            commentService.delete(authUser.getId(), commentId);
      }
}
