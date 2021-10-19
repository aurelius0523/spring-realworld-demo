package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.ArticleModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import com.aurelius.springrealworld.repository.entities.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper extends PageMapper<ArticleEntity, ArticleModel> {
    public ArticleModel toModel(ArticleEntity articleEntity) {
        List<String> tagList = articleEntity
                .getTagEntitySet()
                .stream()
                .map(TagEntity::getName)
                .collect(Collectors.toList());

        return ArticleModel.builder()
                .slug(articleEntity.getSlug())
                .title(articleEntity.getTitle())
                .description(articleEntity.getDescription())
                .body(articleEntity.getBody())
                .tagList(tagList)
                .createdAt(articleEntity.getCreatedAt())
                .modifiedAt(articleEntity.getModifiedAt())
//               .author(authorMapper.fromEntity(articleEntity.getAuthor(), viewerUsername))
                .build();
    }

    public PageModel<ArticleModel> fromPageModel(Page<ArticleEntity> articleEntityPage) {
        PageModel<ArticleModel> pagedArticleModel = super.fromPage(articleEntityPage);
        pagedArticleModel.setData(articleEntityPage
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList()));

        return pagedArticleModel;
    }
}
