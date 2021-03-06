package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.controller.request.CreateArticleRequest;
import com.aurelius.springrealworld.controller.request.UpdateArticleRequest;
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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Random;
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

    public PageModel<ArticleModel> getFeed(String viewerUsername,
                                           int limit,
                                           int offset) {
        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;
        BooleanBuilder where = new BooleanBuilder(qArticleEntity.author.followers.any().username.equalsIgnoreCase(viewerUsername));

        return articleMapper.fromPageModel(
                articleRepository.findAll(where, PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "createdAt"))),
                viewerUsername);
    }

    public PageModel<ArticleModel> getArticleList(String viewerUsername,
                                                  String authorUsername,
                                                  String tag,
                                                  String favouritedBy,
                                                  int limit,
                                                  int offset) {
        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;
        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.hasLength(authorUsername)) {
            where.or(qArticleEntity.author.username.equalsIgnoreCase(authorUsername));
        }

        if (StringUtils.hasLength(tag)) {
            where.or(qArticleEntity.tagEntitySet.any().name.eq(tag));
        }

        if (StringUtils.hasLength(favouritedBy)) {
            where.or(qArticleEntity.favouritedBy.any().username.equalsIgnoreCase(favouritedBy));

        }

        return articleMapper.fromPageModel(
                articleRepository.findAll(where, PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "createdAt"))),
                viewerUsername);
    }

    @Transactional
    public ArticleModel createArticle(CreateArticleRequest createArticleRequest, String username) {
        String slug = slugify(createArticleRequest.getTitle());

        createArticleRequest.getTagList()
                .forEach(this::saveTag);

        UserEntity author = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Set<TagEntity> tagEntities = tagRepository.findByNameIsInIgnoreCase(createArticleRequest.getTagList());

        ArticleEntity articleEntity = ArticleEntity.builder()
                .slug(slug)
                .body(createArticleRequest.getBody())
                .description(createArticleRequest.getDescription())
                .title(createArticleRequest.getTitle())
                .tagEntitySet(tagEntities)
                .author(author)
                .build();

        return articleMapper.toModel(articleRepository.save(articleEntity), username);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void saveTag(String tag) {
        if (tagRepository.findByName(tag).isEmpty()) {
            tagRepository.save(TagEntity.builder().name(tag).build());
        }
    }

    public ArticleModel getArticleBySlug(String slug, String viewerUsername) {
        ArticleEntity articleEntity = articleRepository.getArticleEntityBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with this slug not found"));

        return articleMapper.toModel(articleEntity, viewerUsername);
    }

    @Transactional
    public ArticleModel favouriteArticle(String slug, String favouritedBy) {
        UserEntity favouritedByEntity = userRepository.findByUsernameEqualsIgnoreCase(favouritedBy)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        ArticleEntity articleEntity = articleRepository.getArticleEntityBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with this slug not found"));

        articleEntity.getFavouritedBy().add(favouritedByEntity);

        articleRepository.save(articleEntity);

        return articleMapper.toModel(articleEntity, favouritedBy);
    }

    @Transactional
    public ArticleModel unfavouriteArticle(String slug, String unfavouritedBy) {
        UserEntity unfavouritedByEntity = userRepository.findByUsernameEqualsIgnoreCase(unfavouritedBy)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        ArticleEntity articleEntity = articleRepository.getArticleEntityBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with this slug not found"));

        articleEntity.getFavouritedBy().remove(unfavouritedByEntity);

        articleRepository.save(articleEntity);

        return articleMapper.toModel(articleEntity, unfavouritedBy);
    }

    @Transactional
    public void deleteArticle(String slug) {
        final long articleDeletedCount = articleRepository.deleteBySlug(slug);

        if (articleDeletedCount == 0) {
            throw new ResourceNotFoundException("Article not found");
        }
    }

    @Transactional
    protected String slugify(String title) {
        if (!StringUtils.hasLength(title)) {
            return "";
        }

        String slug = title.toLowerCase().trim().replaceAll("\\s+", "-");

        if (articleRepository.slugExists(slug)) {
            slug += "-" + new Random().nextInt(1000000);
        }

        return slug;
    }

    @Transactional
    public ArticleModel updateArticle(UpdateArticleRequest updateArticleRequest, String slug, String viewerUsername) {
        return articleRepository.findBySlug(slug)
                .map(articleEntity -> {
                    if (StringUtils.hasLength(updateArticleRequest.getBody())) {
                        articleEntity.setBody(updateArticleRequest.getBody());
                    }

                    if (StringUtils.hasLength(updateArticleRequest.getTitle())) {
                        articleEntity.setTitle(updateArticleRequest.getTitle());
                    }

                    if (StringUtils.hasLength(updateArticleRequest.getDescription())) {
                        articleEntity.setDescription(updateArticleRequest.getDescription());
                    }

                    if (!CollectionUtils.isEmpty(updateArticleRequest.getTagList())) {
                        updateArticleRequest.getTagList()
                                .forEach(this::saveTag);

                        articleEntity.setTagEntitySet(tagRepository.findByNameIsInIgnoreCase(updateArticleRequest.getTagList()));
                    }

                    return articleMapper.toModel(articleRepository.save(articleEntity), viewerUsername);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Article with this slug not found"));
    }
}
