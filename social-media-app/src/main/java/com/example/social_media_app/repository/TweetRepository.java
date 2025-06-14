package com.example.social_media_app.repository;


import com.example.social_media_app.model.Tweet;
import com.example.social_media_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUser(User user);

    List<Tweet> findByUserId(Long userId);
}