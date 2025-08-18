package com.example.instagram.newsFeeds.controller;

import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @PostMapping("/newsFeeds")
    public ResponseEntity<NewsFeedSaveResponse> saveNewsFeed(
            @RequestBody NewsFeedSaveRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails // Security 에서 로그인한 사용자
    ){
        return ResponseEntity.ok(newsFeedService.save(request,userDetails.getUserId()));
    }

    @GetMapping("/newsFeeds")
    public ResponseEntity<List<NewsFeedGetResponse>> getNewsFeeds(){
        return ResponseEntity.ok(newsFeedService.findAllNewsFeeds());
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
