package com.example.social_media_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 280)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "tweet_likes",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
