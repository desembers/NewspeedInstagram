package com.example.instagram.comment.service;

import com.example.instagram.comment.dto.request.CommentSaveRequestDto;
import com.example.instagram.comment.dto.request.CommentUpdateRequestDto;
import com.example.instagram.comment.dto.response.CommentResponse;
import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.newsFeeds.Repository.NewsFeedRepository;
import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsFeedRepository newsFeedRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentResponse save(long userId, long feedId ,CommentSaveRequestDto requestDto) {
        NewsFeed newsfeed = newsFeedRepository.findById(feedId).orElseThrow(
                () -> new IllegalArgumentException("해당 피드가 존재하지 않습니다."));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Comment comment = new Comment(newsfeed, user, requestDto.getText());
        commentRepository.save(comment);
        return new CommentResponse(
                comment.getId(),
                user.getId(),
                comment.getNewsFeed().getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // Soft Delete 제외 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findByUserId(long userId) {
        List<Comment> comments = commentRepository.findByUserIdAndDeletedFalse(userId);
        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getNewsFeed().getId(),
                        comment.getText(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponse findOne(long id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getNewsFeed().getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findByNewsFeedId(Long newsFeedId) {
        List<Comment> comments = commentRepository.findAllByNewsFeed_Id_AndDeletedFalse(newsFeedId);

        return  comments.stream().map(comment -> new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getNewsFeed().getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse update(long commentId, long userId, CommentUpdateRequestDto reqeustDto) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당댓글이 존재하지 않습니다"));
        //str.equals(str2) -> str이 null이면 NPE발생
        //Object.equals(str, str2) -> 둘둥 하나가 null이어도 안전하게 반환 (null-safe방식)
        if(!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할수 있습니다.");
        }
        comment.update(reqeustDto.getText());
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getNewsFeed().getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    @Transactional
    public void delete(long commentId, long userId) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글에 존재하지 않습니다."));

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할수 있습니다.");
        }
        comment.softDelete(); // deleted = true
        commentRepository.save(comment); // DB 업데이트
    }
}