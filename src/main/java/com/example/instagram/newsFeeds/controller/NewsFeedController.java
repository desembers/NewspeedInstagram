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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @PostMapping("/newsFeeds")
    public ResponseEntity<NewsFeedSaveResponse> saveNewsFeed(
            @RequestBody NewsFeedSaveRequest request,
            @Auth AuthUser authUser // Security에서 로그인한 사용자
    ){
        return ResponseEntity.ok(newsFeedService.save(request, authUser.getId()));    // AuthUser 객체를 생성하고 @Auth 어노테이션으로 주입
    }

    @GetMapping("/newsFeeds")           //기간별 조회
    public ResponseEntity<Page<NewsFeedGetResponse>> getNewsFeedsByPeriod(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        LocalDateTime startDate = start != null ? start : LocalDateTime.MIN;
        LocalDateTime endDate = end != null ? end : LocalDateTime.now();

        Page<NewsFeedGetResponse> result = newsFeedService.getNewsFeedsByPeriod(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/newsFeeds/{newsFeedId}")
    public ResponseEntity<NewsFeedPatchResponse> updateNewsFeed(
            @RequestBody NewsFeedPatchRequest request,
            @PathVariable Long newsFeedId
    ){
        return ResponseEntity.ok(newsFeedService.updateNewsFeed(newsFeedId,request));
    }

    @DeleteMapping("/newsfeeds/{newsFeedId}")
    public void deleteNewsFeed(
            @PathVariable Long newsFeedId
    ){
        newsFeedService.deleteNewsFeed(newsFeedId);
    }
}
