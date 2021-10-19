package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.facade.mapper.ArticleMapper;
import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.repository.ArticleRepository;
import com.aurelius.springrealworld.repository.entities.QArticleEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import liquibase.util.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ArticleFacade {
    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleFacade(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public PageModel<ArticleModel> getArticleList(String authorUsername, String tag, String favouritedBy, int limit, int offset) {
        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;
        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.hasLength(authorUsername)) {
            where.or(qArticleEntity.author.username.eq(authorUsername));
        }

        if (StringUtils.hasLength(tag)) {
           where.or(qArticleEntity.tagEntitySet.any().name.eq(tag)) ;
        }

        return articleMapper.fromPageModel(articleRepository.findAll(where, PageRequest.of(offset, limit)));
//        return articleMapper.fromPageModel(articleRepository.filterSome(authorUsername, tag, PageRequest.of(offset, limit)));
//        return articleMapper.fromPageModel(articleRepository.findAll(PageRequest.of(offset, limit)));
    }
}
