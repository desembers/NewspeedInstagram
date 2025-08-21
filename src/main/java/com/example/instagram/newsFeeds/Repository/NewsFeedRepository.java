package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface NewsFeedRepository extends JpaRepository<NewsFeed,Long> {
    Page<NewsFeed> findAllByOrderByUpdatedAtDesc (Pageable pageable);

    @Query(value = "SELECT n FROM NewsFeed n WHERE n.updatedAt BETWEEN :start And :end",
            countQuery = "SELECT count(n) FROM NewsFeed n WHERE n.updatedAt BETWEEN :start AND :end")
    Page<NewsFeed> findByUpdatedAtBetween(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end,
                                          Pageable pageable);
}
