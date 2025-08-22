package com.example.instagram.comment.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.comment.dto.request.CommentSaveRequestDto;
import com.example.instagram.comment.dto.request.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

      private CommentService commentService;

      @PostMapping("/comment/{instagramId}")
      public ResponseEntity<CommentResponse> save(
              @Auth AuthUser authUser,
              @PathVariable long instagramId,
              @RequestBody CommentSaveRequestDto requestDto
              ) {
          return ResponseEntity.ok(commentService.save(authUser.getId(), instagramId , requestDto));
      }

      @GetMapping("/comments/{userId}")
      public ResponseEntity<List<CommentResponse>> findId(
              @PathVariable long userid
      )  {
          return ResponseEntity.ok(commentService.findById(userid));
      }

      @PutMapping("/comment/{commentId}")
      public ResponseEntity<CommentResponse> update(
              @Auth AuthUser authUser,
              @PathVariable long commentid,
              @RequestBody CommentUpdateRequestDto requestDto
      ) {
          return ResponseEntity.ok(commentService.update(authUser.getId(), commentid, requestDto));
      }

      @DeleteMapping("/comment/{commentId}")
      public void delete(
              @Auth AuthUser authUser,
              @PathVariable long commentId
      ) {
            commentService.delete(authUser.getId(), commentId);
      }

}
