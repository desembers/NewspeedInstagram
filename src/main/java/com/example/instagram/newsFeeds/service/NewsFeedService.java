package com.example.instagram.newsFeeds.service;

import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.common.exception.UnauthorizedAccessException;
import com.example.instagram.follow.entity.Follow;
import com.example.instagram.follow.repository.FollowRepository;
import com.example.instagram.newsFeeds.Repository.NewsFeedRepository;
import com.example.instagram.newsFeeds.dto.*;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.instagram.auth.dto.AuthUser;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;

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
    public Page<NewsFeedGetResponse> getNewsFeedsByPeriod(AuthUser authUser, LocalDateTime start, LocalDateTime end, Pageable pageable){
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new UnauthorizedAccessException("존재하지 않는 유저아이디입니다.")
        );
        List<Follow> followings = followRepository.findAllByFromUser(user);
        List<User> users = new ArrayList<>(followings.stream().map(e -> e.getToUser()).toList());
        users.add(user);
        List<NewsFeed> newsFeeds = newsFeedRepository.findByUpdatedAtBetweenAndDeletedFalseAndUserInWithComments(start,end, users);

        int startIdx = (int) pageable.getOffset();
        int endIdx = Math.min(startIdx+pageable.getPageSize(), newsFeeds.size());
        List<NewsFeed> pageNewsFeeds =newsFeeds.subList(startIdx,endIdx);

        List<NewsFeedGetResponse> content = pageNewsFeeds.stream()
                .map(nf -> new NewsFeedGetResponse(
                        nf.getId(),
                        nf.getUser().getId(),
                        nf.getContent(),
                        nf.getComments().stream()
                                        .map(c -> new CommentResponse(
                                                c.getId(),
                                                c.getUser().getId(),
                                                c.getNewsFeed().getId(),
                                                c.getText(),
                                                c.getCreatedAt(),
                                                c.getUpdatedAt()
                                        )).collect(Collectors.toList()),
                        nf.getCreatedAt(),
                        nf.getUpdatedAt()
                )).collect(Collectors.toList());
        return new PageImpl<>(content, pageable, newsFeeds.size());
    }

    @Transactional
    public NewsFeedPatchResponse updateNewsFeed(Long newsFeedId, NewsFeedPatchRequest request, AuthUser authUser){
        NewsFeed newsFeed = newsFeedRepository.findByIdAndDeletedFalse(newsFeedId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
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
        NewsFeed newsFeed = newsFeedRepository.findByIdAndDeletedFalse(newsFeedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // Soft Delete
        newsFeed.softDelete();      // deleted = true
        newsFeedRepository.save(newsFeed); // DB 업데이트

        /* Soft Delete 이므로 FK 관계 고려할 필요 X
        // FK 관계 고려: 필요 시 연관 엔티티 null 처리
        // 예: newsFeed.setUser(null);

        try {
            newsFeed.removeUser();
            newsFeedRepository.delete(newsFeed);
            newsFeedRepository.flush(); // 즉시 DB에 반영
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("삭제할 수 없습니다. FK 제약 조건을 확인하세요.", e);
        }*/
    }
}