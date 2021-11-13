package com.aurelius.springrealworld.facade.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleModel {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean favourited;
//    private int favouritesCont;
//    private AuthorDto author;
}
