package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsFeedRepository extends JpaRepository<NewsFeed,Long> {
    Page<NewsFeed> findAllByOrderByCreatedAtDesc (Pageable pageable);
}
