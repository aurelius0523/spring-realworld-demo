package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.controller.request.CreateArticleRequest;
import com.aurelius.springrealworld.facade.ArticleFacade;
import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleFacade articleFacade;

    public ArticleController(ArticleFacade articleFacade) {this.articleFacade = articleFacade;}

    @GetMapping
    public PageModel<ArticleModel> getArticles(@RequestParam(value = "author", required = false) String authorUsername,
                                               @RequestParam(value = "tag", required = false) String tag,
                                               @RequestParam(value = "favourited", required = false) String favouritedBy,
                                               @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
                                               @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        return articleFacade.getArticleList(authorUsername, tag, favouritedBy, limit, offset);
    }

    @PostMapping
    public ArticleModel createArticle(@Valid @RequestBody CreateArticleRequest createArticleRequest,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return articleFacade.createArticle(createArticleRequest, customUserDetails.getUsername());
    }
}
