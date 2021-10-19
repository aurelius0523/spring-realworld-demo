package com.aurelius.springrealworld.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="article_tag")
public class ArticleTagEntity {
    @EmbeddedId
    ArticleTagKey id;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name="article_id")
    ArticleEntity articleEntity;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name="tag_id")
    TagEntity tagEntity;
}
