package com.aurelius.springrealworld.repository.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "bio")
    private String bio;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "user_follow",
            joinColumns = @JoinColumn(name = "followee_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<UserEntity> followers;

    @ManyToMany
    @JoinTable(
            name = "user_follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id")
    )
    private Set<UserEntity> followees;

    @ManyToMany
    @JoinTable(
            name="user_article_favourite",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<ArticleEntity> likedArticles;

    public boolean isFollower(String followerUsername) {
        return followers.stream()
                .anyMatch(follower -> follower.getUsername().equalsIgnoreCase(followerUsername));
    }
}
