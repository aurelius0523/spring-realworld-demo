package com.aurelius.springrealworld.facade.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;
}
