package com.example.instagram.comment.repository;

import com.example.instagram.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserIdAndDeletedFalse(Long userId); // Soft Delete 제외 조회
    Optional<Comment> findByIdAndDeletedFalse(Long id);     // Soft Delete// 제외 단건 조회
    List<Comment> findAllByNewsFeed_Id_AndDeletedFalse(Long newFeedId);

}