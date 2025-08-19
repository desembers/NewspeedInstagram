package com.example.instagram.newsFeeds.Repository;

import com.example.instagram.newsFeeds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
