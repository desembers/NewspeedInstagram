package com.example.instagram.comment.controller;

import com.example.instagram.comment.dto.request.CommentSaveRequestDto;
import com.example.instagram.comment.dto.request.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentCotroller {

      private CommentService commentService;

      @PostMapping("/comment/{instagramId}")
      public ResponseEntity<CommentResponse> save(
              @SessionAttribute(name = "LOGIN_USER") long userId,
              @PathVariable long instagramId,
              @RequestBody CommentSaveRequestDto requestDto
              ) {
          return ResponseEntity.ok(commentService.save(userId, instagramId , requestDto));
      }

      @GetMapping("/comments/{userid}")
      public ResponseEntity<List<CommentResponse>> findId(
              @PathVariable long userid
      )  {
          return ResponseEntity.ok(commentService.findById(userid));
      }

      @PutMapping("/comment/{commentid}")
      public ResponseEntity<CommentResponse> update(
              @SessionAttribute(name = "LOGIN_USER") long userId,
              @PathVariable long commentid,
              @RequestBody CommentUpdateRequestDto requestDto
      ) {
          return ResponseEntity.ok(commentService.update(userId, commentid, requestDto));
      }

      @DeleteMapping("/comment/{commentId}")
      public void delete(
              @SessionAttribute(name = "LOGIN_USER") long userId,
              @PathVariable long commentId
      ) {
            commentService.delete(userId, commentId);
      }

}
