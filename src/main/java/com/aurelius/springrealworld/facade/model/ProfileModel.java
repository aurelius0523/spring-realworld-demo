package com.aurelius.springrealworld.facade.model;

import lombok.Data;

@Data
public class ProfileModel {
    private String username;
    private String bio;
    private String image;
    private boolean isFollowing;
}
