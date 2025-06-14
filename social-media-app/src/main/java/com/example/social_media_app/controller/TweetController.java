package com.example.social_media_app.controller;



import com.example.social_media_app.dto.TweetDTO;
import com.example.social_media_app.model.Tweet;
import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.TweetRepository;
import com.example.social_media_app.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> postTweet(@Valid @RequestBody TweetDTO tweetDTO,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = Tweet.builder()
                .content(tweetDTO.getContent())
                .user(user)
                .build();

        tweetRepository.save(tweet);
        return ResponseEntity.ok("Tweet posted successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> myTweets(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Tweet> tweets = tweetRepository.findByUser(user);
        return ResponseEntity.ok(tweets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        if (!tweet.getUser().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body("You can only delete your own tweets");
        }

        tweetRepository.delete(tweet);
        return ResponseEntity.ok("Tweet deleted");
    }
}
