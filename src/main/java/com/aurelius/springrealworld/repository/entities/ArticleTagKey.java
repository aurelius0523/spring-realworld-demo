package com.aurelius.springrealworld.repository.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ArticleTagKey implements Serializable {
    @Column(name = "article_id")
    Long articleId;

    @Column(name = "tag_id")
    Long tagId;
}
