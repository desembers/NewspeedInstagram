package com.example.instagram.newsFeeds.service;

import com.example.instagram.common.exception.UnauthorizedAccessException;
import com.example.instagram.newsFeeds.Repository.NewsFeedRepository;
import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                savedNewsFeed.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<NewsFeedGetResponse> findAll(Pageable pageable) {
        return newsFeedRepository.findAll(pageable)
                .map(newsFeed -> new NewsFeedGetResponse(
                        newsFeed.getId(),
                        newsFeed.getUser().getId(),
                        newsFeed.getContent(),
                        newsFeed.getCreatedAt(),
                        newsFeed.getUpdatedAt()
                ));
    }

    @Transactional
    public NewsFeedPatchResponse updateNewsFeed(Long newsFeedId, NewsFeedPatchRequest request){
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );

        //수정내용 불일치 -> 본인이 수정할수 있도록 설정 (그렇지 않으면 들어갈수 있습니다)
        if(!(newsFeed.getContent().equals(request.getContent()))) {
            throw new UnauthorizedAccessException("게시글은 본인이 수정할수 있습니다.");
        }


        newsFeed.updateNewsFeed(request.getContent());
        return new NewsFeedPatchResponse(
                newsFeed.getId(),
                newsFeed.getUser().getId(),
                newsFeed.getContent(),
                newsFeed.getCreatedAt(),
                newsFeed.getUpdatedAt()
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

