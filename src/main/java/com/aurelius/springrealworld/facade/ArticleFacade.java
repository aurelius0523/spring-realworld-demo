package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.controller.request.CreateArticleRequest;
import com.aurelius.springrealworld.exception.ResourceNotFoundException;
import com.aurelius.springrealworld.facade.mapper.ArticleMapper;
import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.repository.ArticleRepository;
import com.aurelius.springrealworld.repository.TagRepository;
import com.aurelius.springrealworld.repository.UserRepository;
import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import com.aurelius.springrealworld.repository.entities.QArticleEntity;
import com.aurelius.springrealworld.repository.entities.TagEntity;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class ArticleFacade {
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleFacade(ArticleRepository articleRepository, ArticleMapper articleMapper, UserRepository userRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public PageModel<ArticleModel> getArticleList(String authorUsername, String tag, String favouritedBy, int limit, int offset) {
        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;
        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.hasLength(authorUsername)) {
            where.or(qArticleEntity.author.username.equalsIgnoreCase(authorUsername));
        }

        if (StringUtils.hasLength(tag)) {
            where.or(qArticleEntity.tagEntitySet.any().name.eq(tag));
        }

        return articleMapper.fromPageModel(articleRepository.findAll(where, PageRequest.of(offset, limit)));
    }

    @Transactional
    public ArticleModel createArticle(CreateArticleRequest createArticleRequest, String username) {
        createArticleRequest.getTagList()
                .forEach(this::saveTag);

        UserEntity author = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Set<TagEntity> tagEntities = tagRepository.findByNameIsInIgnoreCase(createArticleRequest.getTagList());

        ArticleEntity articleEntity = ArticleEntity.builder()
                .slug(slugify(createArticleRequest.getTitle()))
                .body(createArticleRequest.getBody())
                .description(createArticleRequest.getDescription())
                .title(createArticleRequest.getTitle())
                .tagEntitySet(tagEntities)
                .author(author)
                .build();

        return articleMapper.toModel(articleRepository.save(articleEntity));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void saveTag(String tag) {
        if (tagRepository.findByName(tag).isEmpty()) {
            tagRepository.save(TagEntity.builder().name(tag).build());
        }
    }

    public ArticleModel getArticleBySlug(String slug) {
        ArticleEntity articleEntity = articleRepository.getArticleEntityBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with this slug not found"));

        return articleMapper.toModel(articleEntity);
    }

    protected String slugify(String title) {
        if (!StringUtils.hasLength(title)) {
            return "";
        }

        return title.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}
