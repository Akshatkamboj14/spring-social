package com.example.social_media_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();
}
