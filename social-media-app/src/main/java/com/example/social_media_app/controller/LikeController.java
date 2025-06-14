package com.example.social_media_app.controller;


import com.example.social_media_app.model.Tweet;
import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.TweetRepository;
import com.example.social_media_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @PostMapping("/{tweetId}")
    public ResponseEntity<?> likeTweet(@PathVariable Long tweetId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        tweet.getLikes().add(user);
        tweetRepository.save(tweet);

        return ResponseEntity.ok("Tweet liked");
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<?> unlikeTweet(@PathVariable Long tweetId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        tweet.getLikes().remove(user);
        tweetRepository.save(tweet);

        return ResponseEntity.ok("Tweet unliked");
    }
}
