package com.aurelius.springrealworld.facade.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    private String email;
    private String username;
    private String bio;
    private String image;
}
