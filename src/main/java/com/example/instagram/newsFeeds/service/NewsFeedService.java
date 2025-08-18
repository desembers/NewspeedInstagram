package com.example.instagram.newsFeeds.service;

import com.example.instagram.newsFeeds.Repository.NewsFeedRepository;
import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final UserRepository userRepository;

    @Transactional // 뉴스피드 생성
    public NewsFeedSaveResponse save(NewsFeedSaveRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        NewsFeed newsFeed = new NewsFeed(request.getContent(), user);
        NewsFeed savedNewsFeed = newsFeedRepository.save(newsFeed);
        return new NewsFeedSaveResponse(
                savedNewsFeed.getId(),
                savedNewsFeed.getUser().getId(),
                savedNewsFeed.getContent(),
                savedNewsFeed.getCreatedAt(),
                savedNewsFeed.getModifiedAt()
        );
    }

    @Transactional(readOnly = true) //뉴스피드 조회 생성
    public List<NewsFeedGetResponse> findAllNewsFeeds() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
        List<NewsFeedGetResponse> dtos = new ArrayList<>();

        for (NewsFeed newsFeed : newsFeeds) {
            NewsFeedGetResponse newsFeedGetResponse = new NewsFeedGetResponse(
                    newsFeed.getId(),
                    newsFeed.getUser().getId(),
                    newsFeed.getContent(),
                    newsFeed.getCreatedAt(),
                    newsFeed.getModifiedAt()
            );
            dtos.add(newsFeedGetResponse);
        }
        return dtos;
    }

    @Transactional
    public NewsFeedPatchResponse updateNewsFeed(Long newsFeedId, NewsFeedPatchRequest request){
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );
        newsFeed.updateNewsFeed(request.getContent());
        return new NewsFeedPatchResponse(
                newsFeed.getId(),
                newsFeed.getUser().getId(),
                newsFeed.getContent(),
                newsFeed.getCreatedAt(),
                newsFeed.getModifiedAt()
        );
    }

    @Transactional
    public void deleteNewsFeed(Long newsFeedId){
        boolean b = newsFeedRepository.existsById(newsFeedId);
        if(!b){
            throw new IllegalArgumentException("존재하지 않는 포스트입니다.");
        }
        newsFeedRepository.deleteById(newsFeedId);
    }
}

