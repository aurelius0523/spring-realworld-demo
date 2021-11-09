package com.aurelius.springrealworld.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateArticleRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String body;

    private String description;
    private List<String> tagList;
}
