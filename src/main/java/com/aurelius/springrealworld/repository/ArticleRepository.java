package com.aurelius.springrealworld.repository;

import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, QuerydslPredicateExecutor<ArticleEntity> {
    Optional<ArticleEntity> getArticleEntityBySlug(String slug);
}
