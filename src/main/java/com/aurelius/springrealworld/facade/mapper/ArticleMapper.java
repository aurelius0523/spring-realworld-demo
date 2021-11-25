package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import com.aurelius.springrealworld.repository.entities.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper extends PageMapper<ArticleEntity, ArticleModel> {
    private final ProfileMapper profileMapper;

    public ArticleMapper(ProfileMapper profileMapper) {this.profileMapper = profileMapper;}

    public ArticleModel toModel(ArticleEntity articleEntity, String viewerUsername) {
        List<String> tagList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(articleEntity.getTagEntitySet())) {
            tagList = articleEntity
                    .getTagEntitySet()
                    .stream()
                    .map(TagEntity::getName)
                    .collect(Collectors.toList());
        }

        return ArticleModel.builder()
                .slug(articleEntity.getSlug())
                .title(articleEntity.getTitle())
                .description(articleEntity.getDescription())
                .body(articleEntity.getBody())
                .tagList(tagList)
                .favourited(articleEntity.isFavouritedBy(viewerUsername))
                .createdAt(articleEntity.getCreatedAt())
                .modifiedAt(articleEntity.getModifiedAt())
                .author(profileMapper.toModel(articleEntity.getAuthor(), viewerUsername))
                .build();
    }

    public PageModel<ArticleModel> fromPageModel(Page<ArticleEntity> articleEntityPage, String viewerUsername) {
        PageModel<ArticleModel> pagedArticleModel = super.fromPage(articleEntityPage);
        pagedArticleModel.setData(articleEntityPage
                .stream()
                .map(articleEntity -> toModel(articleEntity, viewerUsername))
                .collect(Collectors.toList()));

        return pagedArticleModel;
    }
}
