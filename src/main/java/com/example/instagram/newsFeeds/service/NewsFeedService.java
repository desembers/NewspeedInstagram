package com.example.instagram.newsFeeds.service;

import com.example.instagram.common.exception.UnauthorizedAccessException;
import com.example.instagram.newsFeeds.Repository.NewsFeedRepository;
import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.example.instagram.auth.dto.AuthUser;

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
        NewsFeed newsFeed = NewsFeed.of(request.getContent(), user);
        NewsFeed savedNewsFeed = newsFeedRepository.save(newsFeed);
        return new NewsFeedSaveResponse(
                savedNewsFeed.getId(),
                savedNewsFeed.getUser().getId(),
                savedNewsFeed.getContent(),
                savedNewsFeed.getCreatedAt(),
                savedNewsFeed.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)                     //기간별 조회
    public Page<NewsFeedGetResponse> getNewsFeedsByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable){
        Page<NewsFeed> newsFeeds = newsFeedRepository.findByUpdatedAtBetween(start,end,pageable);
        return newsFeeds.map(newsFeed -> new NewsFeedGetResponse(
                newsFeed.getId(),
                newsFeed.getUser().getId(),
                newsFeed.getContent(),
                newsFeed.getCreatedAt(),
                newsFeed.getUpdatedAt()
        ));
    }

    @Transactional
    public NewsFeedPatchResponse updateNewsFeed(Long newsFeedId, NewsFeedPatchRequest request, AuthUser authUser){
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );

        //수정내용 불일치 -> 본인이 수정할수 있도록 설정 (그렇지 않으면 들어갈수 있습니다)
        if (!newsFeed.getUser().getId().equals(authUser.getId())) {
            throw new UnauthorizedAccessException("게시글은 본인만 수정할 수 있습니다.");
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
        NewsFeed newsFeed = newsFeedRepository.findById(newsFeedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // FK 관계 고려: 필요 시 연관 엔티티 null 처리
        // 예: newsFeed.setUser(null);

        try {
            newsFeedRepository.delete(newsFeed);
            newsFeedRepository.flush(); // 즉시 DB에 반영
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("삭제할 수 없습니다. FK 제약 조건을 확인하세요.", e);
        }
    }
}