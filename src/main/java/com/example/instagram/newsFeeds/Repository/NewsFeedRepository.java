package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed,Long> {
}
