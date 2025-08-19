package com.example.instagram.newsFeeds.controller;

import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @PostMapping("/newsFeeds")
    public ResponseEntity<NewsFeedSaveResponse> saveNewsFeed(
            @RequestBody NewsFeedSaveRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails // Security에서 로그인한 사용자
    ){
        //Long testUserId = 1L; // 임시 테스트용 유저 ID
        return ResponseEntity.ok(newsFeedService.save(request,userDetails.getUser().getId()));
    }

    @GetMapping("/newsFeeds")
    public ResponseEntity<Page<NewsFeedGetResponse>> getNewsFeeds(Pageable pageable){
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
