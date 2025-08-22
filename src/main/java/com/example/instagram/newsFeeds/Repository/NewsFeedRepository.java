package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NewsFeedRepository extends JpaRepository<NewsFeed,Long> {

    // Soft Delete 적용: deleted=false 조건 추가
    @Query(value = "SELECT n FROM NewsFeed n WHERE n.deleted = false AND n.updatedAt BETWEEN :start AND :end",
            countQuery = "SELECT count(n) FROM NewsFeed n WHERE n.deleted = false AND n.updatedAt BETWEEN :start AND :end")
    Page<NewsFeed> findByUpdatedAtBetweenAndDeletedFalse(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end,
                                          Pageable pageable);

    Optional<NewsFeed> findByIdAndDeletedFalse(Long id);
}
