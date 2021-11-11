package com.aurelius.springrealworld.facade.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileModel {
    private String username;
    private String bio;
    private String image;
    private boolean isFollowing;
}
