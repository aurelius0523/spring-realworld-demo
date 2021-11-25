package com.aurelius.springrealworld.repository;

import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, QuerydslPredicateExecutor<ArticleEntity> {
    Optional<ArticleEntity> getArticleEntityBySlug(String slug);

    @Query("select (count(a) > 0) from article a where upper(a.slug) = upper(?1)")
    boolean slugExists(String slug);

    long deleteBySlug(String slug);
}
