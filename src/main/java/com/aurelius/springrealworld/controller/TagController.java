package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.controller.response.GetTagListResponse;
import com.aurelius.springrealworld.facade.TagFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    private final TagFacade tagFacade;

    public TagController(TagFacade tagFacade) {this.tagFacade = tagFacade;}

    @GetMapping("/tags")
    public GetTagListResponse getTags() {
        return GetTagListResponse
                .builder()
                .tags(tagFacade.getTagList())
                .build();
    }
}
