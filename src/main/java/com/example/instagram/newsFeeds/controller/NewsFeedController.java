package com.example.instagram.newsFeeds.controller;

import com.example.instagram.auth.annotation.Auth;
import com.example.instagram.auth.dto.AuthUser;
import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @PostMapping("/newsFeeds")
    public ResponseEntity<NewsFeedSaveResponse> saveNewsFeed(
            @RequestBody NewsFeedSaveRequest request,
            @Auth AuthUser authUser // Security에서 로그인한 사용자
    ){
        Long testUserId = 1L; // 임시 테스트용 유저 ID
        return ResponseEntity.ok(newsFeedService.save(request, authUser.getId()));    // AuthUser 객체를 생성하고 @Auth 어노테이션으로 주입
    }

    @GetMapping("/newsFeeds")
    public ResponseEntity<Page<NewsFeedGetResponse>> getNewsFeeds(
            @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(newsFeedService.findAll(pageable));
    }

    @PatchMapping("/newsFeeds/{newsFeedId}")
    public ResponseEntity<NewsFeedPatchResponse> updateNewsFeed(
            @RequestBody NewsFeedPatchRequest request,
            @PathVariable Long newsFeedId
    ){
        return ResponseEntity.ok(newsFeedService.updateNewsFeed(newsFeedId,request));
    }

    @DeleteMapping("/newsFeeds/{newsFeedId}")
    public void deleteNewsFeed(
            @PathVariable Long newsFeedId
    ){
        newsFeedService.deleteNewsFeed(newsFeedId);
    }
}
