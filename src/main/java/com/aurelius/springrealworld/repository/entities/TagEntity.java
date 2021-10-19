package com.aurelius.springrealworld.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tag")
@Table(name = "tag", schema = "realworld")
public class TagEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tagEntitySet")
    private Set<ArticleEntity> articleEntitySet;

    @JsonIgnore
    public Set<ArticleEntity> getArticleEntitySet() {
        return articleEntitySet;
    }
}
