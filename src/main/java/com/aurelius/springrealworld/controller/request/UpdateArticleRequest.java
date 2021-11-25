package com.aurelius.springrealworld.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UpdateArticleRequest {
    private String title;
    private String body;
    private String description;
    private List<String> tagList;
}
