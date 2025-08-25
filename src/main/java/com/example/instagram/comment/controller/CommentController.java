package com.example.instagram.comment.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.comment.dto.request.CommentSaveRequestDto;
import com.example.instagram.comment.dto.request.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/newsfeeds/{newsFeedId:\\d+}/comments")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<CommentResponse> save(
            @Auth AuthUser authUser,
            @PathVariable Long newsFeedId,
            @Valid @RequestBody CommentSaveRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(authUser.getId(), newsFeedId, requestDto));
    }

    @GetMapping("/users/{userId:\\d+}/comments")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<List<CommentResponse>> findId(
            @PathVariable long userId
    ) {
        return ResponseEntity.ok(commentService.findByUserId(userId));
    }

    @GetMapping("/comments/{commentId:\\d+}")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<CommentResponse> findOne(
            @PathVariable long commentId
    ) {
        return ResponseEntity.ok(commentService.findOne(commentId));
    }

    @GetMapping("/newsfeeds/{newsFeedId:\\d+}/comments")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<List<CommentResponse>> findByNewsFeedId(
            @PathVariable Long newsFeedId
    ) {
        return ResponseEntity.ok(commentService.findByNewsFeedId(newsFeedId));
    }

    @PutMapping("/newsfeeds/comments/{commentId:\\d+}")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<CommentResponse> update(
            @Auth AuthUser authUser,
            @PathVariable long commentId,
            @Valid @RequestBody CommentUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(commentService.update(commentId, authUser.getId(), requestDto));
    }

    @DeleteMapping("/newsfeeds/comments/{commentId:\\d+}")    // 숫자가 아닐 시, HTTP 에러 코드 404 자동 반환
    public ResponseEntity<Void> delete(
            @Auth AuthUser authUser,
            @PathVariable long commentId
    ) {
        commentService.delete(commentId, authUser.getId());
        return ResponseEntity.noContent().build();    // 코드 204
    }
}
