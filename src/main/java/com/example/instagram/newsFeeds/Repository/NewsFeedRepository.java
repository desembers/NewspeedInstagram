package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    Optional<NewsFeed> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT DISTINCT n FROM NewsFeed n " +
            "LEFT JOIN FETCH n.comments c " +
            "LEFT JOIN FETCH c.user " +
            "WHERE n.deleted =false AND (c.deleted = false OR c.id IS NULL) " +
            "AND n.updatedAt BETWEEN :start AND :end AND n.user In (:users) " +
            "ORDER BY n.updatedAt DESC"
    )
    List<NewsFeed> findByUpdatedAtBetweenAndDeletedFalseAndUserInWithComments(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("users") List<User> users);
}
