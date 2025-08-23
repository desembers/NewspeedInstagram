package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import com.example.instagram.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    Optional<NewsFeed> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT n FROM NewsFeed n WHERE n.deleted =false AND n.updatedAt BETWEEN :start AND :end AND n.user In (:users)",
            countQuery = "SELECT count(n) FROM NewsFeed n WHERE n.deleted=false AND n.updatedAt BETWEEN :start AND :end AND n.user IN (:users)"
    )
    Page<NewsFeed> findByUpdatedAtBetweenAndDeletedFalseAndUserIn(@Param("start") LocalDateTime start,
                                                                  @Param("end") LocalDateTime end,
                                                                  @Param("users") List<User> users,
                                                                  Pageable pageable);
}
