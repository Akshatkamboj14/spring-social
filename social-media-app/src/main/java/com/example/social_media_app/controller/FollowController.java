package com.example.social_media_app.controller;


import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final UserRepository userRepository;

    @PostMapping("/{username}")
    public ResponseEntity<?> followUser(@PathVariable String username,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        User follower = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User followee = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        follower.getFollowing().add(followee);
        userRepository.save(follower);

        return ResponseEntity.ok("Followed " + username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> unfollowUser(@PathVariable String username,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User follower = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User followee = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        follower.getFollowing().remove(followee);
        userRepository.save(follower);

        return ResponseEntity.ok("Unfollowed " + username);
    }
}
