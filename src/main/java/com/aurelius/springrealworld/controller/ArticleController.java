package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.facade.ArticleFacade;
import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleFacade articleFacade;

    @GetMapping
    public PageModel<ArticleModel> getArticles(@RequestParam(value = "author", required = false) String authorUsername,
                                               @RequestParam(value = "tag", required = false) String tag,
                                               @RequestParam(value = "favourited", required = false) String favouritedBy,
                                               @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
                                               @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        return articleFacade.getArticleList(authorUsername, tag, favouritedBy, limit, offset);
    }
}
